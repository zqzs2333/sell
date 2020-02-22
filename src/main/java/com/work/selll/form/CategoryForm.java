package com.work.selll.form;

import lombok.Data;

import java.sql.Date;

@Data
public class CategoryForm {


    private Integer categoryId;
    private String categoryName;
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;
}
