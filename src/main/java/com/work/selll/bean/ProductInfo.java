package com.work.selll.bean;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    private  String productName;

    private BigDecimal productPrice;

    private  Integer productStock;

    private String productDescription;

    private String productIcon;

    /** 1 z  0 x**/
    private  Integer productStatus;

    private  Integer categoryType;

}
