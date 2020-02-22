package com.work.selll.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.work.selll.DTO.OrderDTO;
import com.work.selll.Util.JsonUtil;
import com.work.selll.Util.MathUtil;
import com.work.selll.exception.SellException;
import com.work.selll.service.OrderService;
import com.work.selll.service.PayService;
import enums.ResultEnums;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PayServiceImpl implements PayService {
    private final static String order_name ="微信点餐订单";
    @Autowired
    private BestPayServiceImpl bestPayService;
    @Autowired
    OrderService orderService;
    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(order_name);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        PayResponse payResponse = bestPayService.pay(payRequest);
        return payResponse;

    }

    @Override
    public PayResponse notify(String notifyData) {
        //验证签名
        //支付的状态

        //支付的金额  todo
        //支付人
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("微信支付-异步通知， payResponse={}", JsonUtil.toJson(payResponse));

        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if (orderDTO == null) {
            log.error("微信支付-订单不存在");
            throw new SellException(ResultEnums.order_not_exist);
        }
        if (!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())) {
            log.error("微信支付-异步通知，订单金额不一致，orderId={} , 微信通知金额={},系统金额={}",
                    payResponse.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnums.wxpay_notify_money_error);
        }
        orderService.paid(orderDTO);
        //告诉微信 不要再发送异步通知 订单状态已经成功的修改了
        return payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest =new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        RefundResponse refund = bestPayService.refund(refundRequest);
        return refund;
    }
}
