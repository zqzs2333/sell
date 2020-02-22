package com.work.selll.service;

import com.work.selll.DTO.CartDTO;
import com.work.selll.bean.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne (String ProductId);
    Page<ProductInfo> findAll(Pageable pageable);

    /**
     * 查询在架的商品
     **/
    List<ProductInfo> findUpAll();
    ProductInfo save(ProductInfo productInfo);
    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);
}
