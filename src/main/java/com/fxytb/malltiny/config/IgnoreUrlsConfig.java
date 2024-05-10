package com.fxytb.malltiny.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Data
@Configuration
@ConfigurationProperties("security.ignore")
public class IgnoreUrlsConfig {

    /**
     * 可放过url
     */
    private List<String> urls = new ArrayList<>();

}
