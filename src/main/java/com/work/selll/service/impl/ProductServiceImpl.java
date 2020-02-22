package com.work.selll.service.impl;

import com.work.selll.DTO.CartDTO;
import com.work.selll.Util.ResultVoUtil;
import com.work.selll.VO.ResultVo;
import com.work.selll.bean.ProductInfo;
import com.work.selll.dao.productInfoDao;
import com.work.selll.exception.SellException;
import com.work.selll.service.ProductService;
import enums.PayStastusEnums;
import enums.ProductStatusEnums;
import enums.ResultEnums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    productInfoDao productInnfoDao;
    @Override
    public ProductInfo findOne(String ProductId) {
        return productInnfoDao.findById(ProductId).orElse(null);
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInnfoDao.findAll(pageable);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInnfoDao.findByProductStatus(ProductStatusEnums.UP.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInnfoDao.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {

            ProductInfo productInfo = productInnfoDao.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null)
            {
                throw  new SellException(ResultEnums.product_not_exist);
            }
            Integer i = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(i);
            productInnfoDao.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {

            ProductInfo productInfo = productInnfoDao.findById(cartDTO.getProductId()).orElse(null);
            if (productInfo == null)
            {
                throw  new SellException(ResultEnums.product_not_exist);
            }
            Integer i = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (i<0)
            {
                throw new SellException(ResultEnums.product_stock_error);
            }
            productInfo.setProductStock(i);
            productInnfoDao.save(productInfo);
        }

    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = productInnfoDao.findById(productId).orElse(null);
        if (productInfo ==null) {
            throw new SellException(ResultEnums.product_not_exist);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnums.UP.getCode())) {
            throw new SellException(ResultEnums.seller_product_status_error);
        }
        productInfo.setProductStatus(ProductStatusEnums.UP.getCode());
        return productInnfoDao.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = productInnfoDao.findById(productId).orElse(null);
        if (productInfo ==null) {
            throw new SellException(ResultEnums.product_not_exist);
        }
        if (productInfo.getProductStatus().equals(ProductStatusEnums.DOWN.getCode())) {
            throw new SellException(ResultEnums.seller_product_status_error);
        }
        productInfo.setProductStatus(ProductStatusEnums.DOWN.getCode());
        return productInnfoDao.save(productInfo);
    }
}
