package com.work.selll.service.impl;

import com.work.selll.bean.SellerInfo;
import com.work.selll.dao.SellerInfoDao;
import com.work.selll.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    SellerInfoDao sellerInfoDao;

    @Override
    public SellerInfo findByOpenid(String openid) {
        SellerInfo sellerInfo = sellerInfoDao.findByOpenid(openid);
        return sellerInfo;
    }
}
