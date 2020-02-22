package com.work.selll.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "url")
@Component
public class ProjectUrlConfig {
    public String wxMpUrl;
    public String wxOpUrl;
    public String sell;
}
