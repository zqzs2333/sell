package com.work.selll.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.work.selll.DTO.OrderDTO;
import com.work.selll.bean.OrderDetail;
import com.work.selll.exception.SellException;
import com.work.selll.form.OrderForm;
import enums.ResultEnums;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class OrderForm2OrderDTO {
    public static OrderDTO convert(OrderForm orderForm)
    {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList =new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e)
        {
            log.error("转化出错！");
            throw new SellException(ResultEnums.param_error);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
