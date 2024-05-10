package com.fxytb.malltiny.config;

import cn.hutool.core.util.ArrayUtil;
import com.fxytb.malltiny.component.JwtAuthenticationTokenFilter;
import com.fxytb.malltiny.component.RestAuthenticationEntryPoint;
import com.fxytb.malltiny.component.RestfulAccessDeniedHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private RestfulAccessDeniedHandler restfulAccessDeniedHandler;

    @Autowired
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;


    @SneakyThrows
    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) {

        httpSecurity.csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, ArrayUtil.toArray(ignoreUrlsConfig.getUrls(), String.class))
                .permitAll()
                .antMatchers("/umsAdmin/login")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()
                .anyRequest()
                .authenticated();

        //禁用缓存
        httpSecurity.
                headers().
                cacheControl().
                disable();

        //添加JWT filter
        httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        //添加自定义未授权和登录结果返回
        httpSecurity.exceptionHandling()
                .accessDeniedHandler(restfulAccessDeniedHandler)
                .authenticationEntryPoint(restAuthenticationEntryPoint);

        return httpSecurity.build();
    }





}
