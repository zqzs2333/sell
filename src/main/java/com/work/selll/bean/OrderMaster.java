package com.work.selll.bean;

import enums.OrderStatusEnums;
import enums.PayStastusEnums;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;
    private BigDecimal orderAmount;

    private Integer orderStatus= OrderStatusEnums.NEW.getCode();

    private Integer payStatus= PayStastusEnums.WAIT.getCode();
    private Date createTime;
    private Date updateTime;
}
