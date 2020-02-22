package com.work.selll.service.impl;

import com.work.selll.DTO.OrderDTO;
import com.work.selll.bean.OrderDetail;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class OrderServiceImplTest {

    @Autowired
    OrderServiceImpl orderService;
    private  final String openId ="asd123";
    private  final String orderId="1581735615135767855";
    @Test
    public void  create()
    {
        OrderDTO orderDTO =new OrderDTO();
        orderDTO.setBuyerAddress("不知偶倒");
        orderDTO.setBuyerName("zz");
        orderDTO.setBuyerPhone("54444");
        orderDTO.setBuyerOpenid(openId);
        List<OrderDetail> orderDetailList =new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("1");
        o1.setProductQuantity(1);
        orderDetailList.add(o1);
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.info("result={}",result);
    }
    @Test
    public void findone()
    {
        OrderDTO orderDTO = orderService.findOne(orderId);
        log.info("结果：orderDTO={}",orderDTO);
    }
    @Test
    public void findAll()
    {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<OrderDTO> all = orderService.findAll(openId, pageRequest);
        log.info("结果：all={}",all.getTotalElements());
    }
    @Test
    public void cancel()
    {
        OrderDTO orderDTO = orderService.findOne(orderId);
        OrderDTO result = orderService.paid(orderDTO);
        log.info("结果：result={}",result);
    }

}