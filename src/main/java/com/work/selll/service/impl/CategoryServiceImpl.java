package com.work.selll.service.impl;

import com.work.selll.bean.productCategory;
import com.work.selll.dao.productCategoryDao;
import com.work.selll.service.categoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements categoryService {
    @Autowired
   private productCategoryDao productCategoryDao;
    @Override
    public productCategory findOne(Integer CategoryId) {
        return productCategoryDao.findById(CategoryId).orElse(null);
    }

    @Override
    public List<productCategory> findAll() {
        return productCategoryDao.findAll();
    }

    @Override
    public List<productCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return productCategoryDao.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public productCategory save(productCategory productCategory) {
        return productCategoryDao.save(productCategory);
    }
}
