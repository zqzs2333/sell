package com.work.selll.dao;

import com.work.selll.bean.productCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface productCategoryDao extends JpaRepository<productCategory,Integer> {
    List<productCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
