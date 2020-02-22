package com.work.selll.service;

import com.work.selll.bean.SellerInfo;

public interface SellerService {
    SellerInfo findByOpenid(String openid);
}
