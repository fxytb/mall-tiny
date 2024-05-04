package com.fxytb.malltiny.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.fxytb.malltiny.dao.mbg", "com.fxytb.malltiny.dao"})
public class MyBatisConfig {
}
