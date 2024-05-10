package com.fxytb.malltiny.component;

import com.fxytb.malltiny.common.utils.JwtTokenUtil;
import com.fxytb.malltiny.sevice.UserAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final UserAdminService userAdminService;

    private final JwtTokenUtil jwtTokenUtil;

    private JwtAuthenticationTokenFilter(UserAdminService userAdminService, JwtTokenUtil jwtTokenUtil) {
        this.userAdminService = userAdminService;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 设置jwt令牌
     *
     * @param request
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);
        if (authHeader != null && authHeader.startsWith(this.tokenHead)) {
            String authToken = authHeader.substring(this.tokenHead.length());// The part after "Bearer "
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            log.info("校验用户:{}", username);
            SecurityContext context = SecurityContextHolder.getContext();
            if (username != null && context.getAuthentication() == null) {
                UserDetails userDetails = this.userAdminService.getAdminByUsername(username);
                if (jwtTokenUtil.isValidToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    log.info("登录用户:{}", username);
                    context.setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}

