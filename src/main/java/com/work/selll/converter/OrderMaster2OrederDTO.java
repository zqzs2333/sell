package com.work.selll.converter;

import com.work.selll.DTO.OrderDTO;
import com.work.selll.bean.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrederDTO {
    public static OrderDTO convert(OrderMaster orderMaster)
    {
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }
    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList)
    {
       return orderMasterList.stream().map( e -> convert(e)).collect(Collectors.toList());
    }
}
