package com.work.selll.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WechatOpenConfig {
    @Autowired
    private WeChatAccountConfig accountConfig;
    @Bean
    public WxMpService WxOpenService()
    {
        WxMpService WxOpenService =new WxMpServiceImpl();
        WxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
        return WxOpenService;
    }
    @Bean
    public WxMpConfigStorage wxOpenConfigStorage()
    {
        WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setSecret(accountConfig.getOpenAppSecret());
        wxMpInMemoryConfigStorage.setAppId(accountConfig.getOpenAppId());
        return wxMpInMemoryConfigStorage;
    }
}
