package com.fxytb.malltiny.generator;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class Generator {

    public static void main(String[] args) {

        try (InputStream is = Generator.class.getResourceAsStream("/generatorConfig.xml")) {
            //记录warnings日志
            List<String> warnings = new ArrayList<>();
            //当生成的代码重复时,覆盖原代码
            boolean overwrite = true;
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(is);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            //创建MBG
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            //执行生成代码
            myBatisGenerator.generate(null);
            //打印warn日志
            if (!warnings.isEmpty()) {
                log.warn("mybatis代码生成器执行失败,警告信息:{}", warnings);
            }
        } catch (Exception e) {
            log.error("mybatis代码生成器执行异常,异常信息:{}", e.getMessage(), e);
        }

    }
}