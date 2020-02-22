package com.work.selll.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
//商品详情
public class ProductInfoVo {
    @JsonProperty("id")
    private  String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("descriprion")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
