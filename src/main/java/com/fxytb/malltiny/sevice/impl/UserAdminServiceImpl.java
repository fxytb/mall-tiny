package com.fxytb.malltiny.sevice.impl;

import cn.hutool.core.collection.CollUtil;
import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.common.utils.JwtTokenUtil;
import com.fxytb.malltiny.model.bo.AdminUserDetails;
import com.fxytb.malltiny.model.param.UserAdminLoginParam;
import com.fxytb.malltiny.sevice.UserAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {

    /**
     * 存放默认用户信息
     */
    private List<AdminUserDetails> adminUserDetailsList = new ArrayList<>();
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {
        adminUserDetailsList.add(AdminUserDetails.builder().username("admin").password(passwordEncoder.encode("123456")).authorityList(CollUtil.toList("ROLE_ADMIN")).build());
        adminUserDetailsList.add(AdminUserDetails.builder().username("macro").password(passwordEncoder.encode("123456")).authorityList(CollUtil.toList("ROLE_USER")).build());
    }

    @Override
    public AdminUserDetails getAdminByUsername(String username) {
        List<AdminUserDetails> findList = adminUserDetailsList.stream().filter(item -> item.getUsername().equals(username)).collect(Collectors.toList());
        if (CollUtil.isNotEmpty(findList)) {
            return findList.get(0);
        }
        return null;
    }


    @Override
    public String login(UserAdminLoginParam loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        try {
            UserDetails userDetails = getAdminByUsername(username);
            if (userDetails == null) {
                return null;
            }
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                return null;
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenUtil.generateToken(userDetails);
        } catch (AuthenticationException e) {
            log.warn("用户登录异常,异常信息:{}", e.getMessage(), e);
            return null;
        }
    }
}
