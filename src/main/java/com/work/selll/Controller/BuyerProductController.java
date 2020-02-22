package com.work.selll.Controller;

import com.work.selll.Util.ResultVoUtil;
import com.work.selll.VO.ProductInfoVo;
import com.work.selll.VO.ProductVo;
import com.work.selll.VO.ResultVo;
import com.work.selll.bean.ProductInfo;
import com.work.selll.bean.productCategory;
import com.work.selll.service.ProductService;
import com.work.selll.service.categoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    ProductService productService;
    @Autowired
    com.work.selll.service.categoryService categoryService;
    @GetMapping("/list")
    public ResultVo  list()
    {  //1：查询所有的上架的商品
        List<ProductInfo> upAllList = productService.findUpAll();
        //查询所有的类目
        List<Integer> categoryTypeList =  upAllList.stream().map(e -> e.getCategoryType()).collect(Collectors.toList());
        List<productCategory> productCategoryList = categoryService.findByCategoryTypeIn(categoryTypeList);
        //数据拼装
        List<ProductVo> ProductVoList =new ArrayList<>();
        for (productCategory productCategory : productCategoryList) {
            ProductVo productVo =new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategorytype(productCategory.getCategoryType());
            List<ProductInfoVo> ProductInfoList =new ArrayList<>();
            for (ProductInfo productInfo : upAllList) {
               if (productInfo.getCategoryType().equals(productCategory.getCategoryType()))
               {
                   ProductInfoVo productInfoVo = new ProductInfoVo();
                   BeanUtils.copyProperties(productInfo,productInfoVo);
                   ProductInfoList.add(productInfoVo);
               }

            }
            productVo.setProductInfoVoList(ProductInfoList);
            ProductVoList.add(productVo);

        }


        return ResultVoUtil.success(ProductVoList);

    }

}
