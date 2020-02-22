package com.work.selll.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WeChatAccountConfig {
    private String mpAppId;
    private String mpAppSecret;
    private String openAppId;
    private String openAppSecret;
    private String mchId;
    private String mchKey;
    private String mchPath;
    private String notifyUrl;

}
