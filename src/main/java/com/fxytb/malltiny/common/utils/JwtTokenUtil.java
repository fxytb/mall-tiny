package com.fxytb.malltiny.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.fxytb.malltiny.common.enums.JwtEnum.CLAIM_KEY_CREATED;
import static com.fxytb.malltiny.common.enums.JwtEnum.CLAIM_KEY_USERNAME;

/**
 * JwtToken生成的工具类
 * JWT token的格式：header.payload.signature
 * header的格式（算法、token的类型）：
 * {"alg": "HS512","typ": "JWT"}
 * payload的格式（用户名、创建时间、生成时间）：
 * {"sub":"wang","created":1489079981393,"exp":1489684781}
 * signature的生成算法：
 * HMACSHA512(base64UrlEncode(header) + "." +base64UrlEncode(payload),secret)
 */
@Slf4j
@Component
@Setter
public class JwtTokenUtil {

    /**
     * 密钥
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * 过期时间(单位秒)
     */
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * 根据用户信息生成token
     *
     * @param user
     * @return token
     */
    public String generatorTokenByUserDetails(UserDetails user) {
        String token = null;
        try {
            Map<String, Object> claims = new HashMap<>();
            claims.put(CLAIM_KEY_USERNAME.getAttribute(), user.getUsername());
            claims.put(CLAIM_KEY_CREATED.getAttribute(), DateUtil.date());
            token = generatorToken(claims);
            return token;
        } catch (Exception e) {
            log.error("用户信息token生成异常,异常信息:{}", e.getMessage(), e);
            return token;
        }
    }

    /**
     * 根据claim生成token
     *
     * @param claims
     * @return token
     */
    private String generatorToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generatorExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }


    /**
     * 从token中获取claims
     *
     * @param token
     * @return claims
     */
    private Claims getClaimsFromToken(String token) {
        Claims body = null;
        try {
            body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token).getBody();
            return body;
        } catch (Exception e) {
            log.error("token获取Claim异常,异常信息:{}", e.getMessage(), e);
            return body;
        }
    }

    /**
     * 从token中获取用户名称
     *
     * @param token
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        String userName = null;
        try {
            Claims claimsFromToken = getClaimsFromToken(token);
            userName = claimsFromToken.getSubject();
            return userName;
        } catch (Exception e) {
            log.error("token获取用户名称异常,异常信息:{}", e.getMessage(), e);
            return userName;
        }
    }

    /**
     * 从token中获取失效时间
     *
     * @param token
     * @return 失效时间
     */
    private Date getExpiredDateFromToken(String token) {
        Date expiredDate = null;
        try {
            Claims claimsFromToken = getClaimsFromToken(token);
            expiredDate = claimsFromToken.getExpiration();
            return expiredDate;
        } catch (Exception e) {
            log.error("token获取过期时间异常,异常信息:{}", e.getMessage(), e);
            return expiredDate;
        }
    }

    /**
     * 生成token过期时间
     *
     * @return 过期时间
     */
    private Date generatorExpirationDate() {
        return DateUtil.date(System.currentTimeMillis() + expiration * 1000);
    }

    //校验token

    /**
     * 判断是否有效token
     *
     * @param token
     * @param user
     * @return 是否有效
     */
    public boolean isValidToken(String token, UserDetails user) {
        String username = getUsernameFromToken(token);
        Date expiredDate = getExpiredDateFromToken(token);
        return StrUtil.equals(username, user.getUsername()) && !isExpiredDate(expiredDate);
    }

    /**
     * 判断是否失效时间
     *
     * @param expiredDate
     * @return 是否失效
     */
    private boolean isExpiredDate(Date expiredDate) {
        return DateUtil.compare(expiredDate, DateUtil.date()) < 0;
    }


    /**
     * 判断当前token是否可刷新
     *
     * @param expiredDate
     * @return 是否可刷新
     */
    private boolean isRefreshableToken(Date expiredDate) {
        return !isExpiredDate(expiredDate);
    }

    //刷新token

    /**
     * 刷新token
     *
     * @param token
     */
    private void refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.setExpiration(DateUtil.date());
    }


    @Bean
    private Object aa(){
        return new Date().toString();
    }

}