package com.work.selll.service.impl;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.work.selll.bean.ProductInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ProductServiceImplTest {
    @Autowired
    ProductServiceImpl productService;
    @Test
    void findOne() {
        ProductInfo result = productService.findOne("1");
        System.out.print(result.toString());
    }

    @Test
    void findAll() {
        PageRequest pageRequest = PageRequest.of(0, 2);
        Page<ProductInfo> result = productService.findAll(pageRequest);
        System.out.print(result.getTotalElements());
    }


    @Test
    void findUpAll() {
    }

    @Test
    void save() {
        
    }
}