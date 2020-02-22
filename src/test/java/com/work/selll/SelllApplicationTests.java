package com.work.selll;

import com.work.selll.bean.ProductInfo;
import com.work.selll.bean.productCategory;
import com.work.selll.dao.productCategoryDao;
import com.work.selll.dao.productInfoDao;
import com.work.selll.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class SelllApplicationTests {
@Autowired
productCategoryDao Dao;
@Autowired
CategoryServiceImpl categoryService;
    @Test
    void contextLoads() {
    }
    @Test
    void productTest()
    {
        Optional<productCategory> productCategory = Dao.findById(1);
         System.out.print(productCategory.toString());
    }
    @Test
    @Transactional
    void productSave()
    {
        productCategory p =new productCategory();
        p.setCategoryId(2);
        p.setCategoryName("女生最爱");
        p.setCategoryType(3);
        Dao.save(p);

    }
    @Test
    void findByCategoryTypeIn()
    {
        List<Integer> list = Arrays.asList(2,3);
        List<productCategory> result = categoryService.findByCategoryTypeIn(list);
        System.out.print(result.size());

    }
    @Autowired
    productInfoDao productInfoDao;
    @Test
    void findByType(){
        List<ProductInfo> result = productInfoDao.findByProductStatus(0);
        System.out.print(result.size());
    }
}
