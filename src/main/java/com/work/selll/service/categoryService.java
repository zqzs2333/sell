package com.work.selll.service;

import com.work.selll.bean.productCategory;

import java.util.List;

public interface categoryService {
    productCategory findOne(Integer CategoryId);
    List<productCategory> findAll();
    List<productCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    productCategory save(productCategory productCategory);
}
