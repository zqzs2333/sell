package com.work.selll.form;

import enums.ProductStatusEnums;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductForm {
    private String productId;

    private  String productName;

    private BigDecimal productPrice;

    private  Integer productStock;

    private String productDescription;

    private String productIcon;

    /** 1 z  0 x**/
    private  Integer productStatus = ProductStatusEnums.UP.getCode();

    private  Integer categoryType;
}
