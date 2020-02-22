package com.work.selll.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.work.selll.bean.ProductInfo;
import lombok.Data;

import java.util.List;

@Data
public class ProductVo {
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categorytype;

    @JsonProperty("foods")
    private List<ProductInfoVo> ProductInfoVoList;


}
