package com.work.selll.service.impl;

import com.lly835.bestpay.model.RefundResponse;
import com.work.selll.DTO.CartDTO;
import com.work.selll.DTO.OrderDTO;
import com.work.selll.Util.KeyUtil;
import com.work.selll.bean.OrderDetail;
import com.work.selll.bean.OrderMaster;
import com.work.selll.bean.ProductInfo;
import com.work.selll.converter.OrderMaster2OrederDTO;
import com.work.selll.dao.OrderDetailDao;
import com.work.selll.dao.OrderMasterDao;
import com.work.selll.exception.SellException;
import com.work.selll.service.OrderService;
import com.work.selll.service.PayService;
import com.work.selll.service.ProductService;
import com.work.selll.service.WebSocket;
import enums.OrderStatusEnums;
import enums.PayStastusEnums;
import enums.ResultEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    WebSocket webSocket;
    @Autowired
    OrderMasterDao orderMasterDao;
    @Autowired
    ProductService productService;
    @Autowired
    OrderDetailDao orderDetailDao;
    @Autowired
    PayService payService;
    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId=KeyUtil.getUniqueKey();
        BigDecimal orderAmount =new BigDecimal(0);
//        List<CartDTO> cartDTOList =new ArrayList<>();
        // 数量 价格（商品）
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if (productInfo == null)
            {
                throw  new SellException(ResultEnums.product_not_exist);
            }
            //判断数量是否够
            //        计算总价
          orderAmount =  productInfo.getProductPrice().
                  multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            //        写入数据库
            orderDetail.setDetailId(KeyUtil.getUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailDao.save(orderDetail);
//            CartDTO cartDTO =new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity())
//            cartDTOList.add(cartDTO);
        }
        OrderMaster orderMaster =new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnums.NEW.getCode());
        orderMaster.setPayStatus(PayStastusEnums.WAIT.getCode());
        orderMasterDao.save(orderMaster);


//        扣库存
        List<CartDTO> cartDTOList  =  orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
       //发送消息通知
        webSocket.sendMessage("有新的订单");
        return orderDTO;


    }

    @Override
    public OrderDTO findOne(String orderID) {
        OrderMaster orderMaster = orderMasterDao.findById(orderID).orElse(null);
        if (orderMaster == null)
        {
            throw new SellException(ResultEnums.order_not_exist);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByOrderId(orderID);
        if (CollectionUtils.isEmpty(orderDetailList))
        {
            throw new SellException(ResultEnums.orderDetail_not_exist);
        }
        OrderDTO orderDTO =new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;

    }

    @Override
    public Page<OrderDTO> findAll(String buyerOpenID, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findByBuyerOpenid(buyerOpenID, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrederDTO.convert(orderMasterPage.getContent());
        PageImpl<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());

        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();


        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode()))
        {
            throw new SellException(ResultEnums.orderStutas_is_error);
        }

//        根据状态是否能修改状态
        orderDTO.setOrderStatus(OrderStatusEnums.CANCEl.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null)
        {
            log.info("取消订单-更新失败，{}",orderMaster);
            throw new SellException(ResultEnums.orderStutas_update_error);
        }
//        返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList()))
        {
            log.info("取消订单-订单中没有商品");
            throw new SellException(ResultEnums.order_detail_empty);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(e ->
                new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
//        如果已经支付，退款
        if (orderDTO.getPayStatus().equals(PayStastusEnums.SUCCESS))
        {
             payService.refund(orderDTO);
        }

        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnums.NEW.getCode()))
        {
            throw new SellException(ResultEnums.orderStutas_is_error);
        }
        orderDTO.setOrderStatus(OrderStatusEnums.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null)
        {
            log.info("取消订单-更新失败，{}",orderMaster);
            throw new SellException(ResultEnums.orderStutas_update_error);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();


        //判断订单状态
        if (!orderDTO.getPayStatus().equals(PayStastusEnums.WAIT.getCode()))
        {
            throw new SellException(ResultEnums.payStutas_is_error);
        }
        orderDTO.setPayStatus(PayStastusEnums.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterDao.save(orderMaster);
        if (updateResult == null)
        {
            log.info("完成订单-更新失败，{}",orderMaster);
            throw new SellException(ResultEnums.payStutas_update_error);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterDao.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrederDTO.convert(orderMasterPage.getContent());
        PageImpl<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());

        return orderDTOPage;
    }
}
