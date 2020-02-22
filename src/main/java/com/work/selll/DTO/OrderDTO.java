package com.work.selll.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.work.selll.Util.serializer.Date2Long;
import com.work.selll.bean.OrderDetail;
import enums.OrderStatusEnums;
import enums.PayStastusEnums;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;
    private BigDecimal orderAmount;

    private Integer orderStatus;

    private Integer payStatus;
    @JsonSerialize(using = Date2Long.class)
    private Date creatTime;
    @JsonSerialize(using = Date2Long.class)
    private Date updateTime;
    List<OrderDetail> orderDetailList = new ArrayList<>();
}
