package com.fxytb.malltiny.component;

import cn.hutool.json.JSONUtil;
import com.fxytb.malltiny.common.result.CommonResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fxytb.malltiny.common.enums.MessageEnum.ERROR;
import static com.fxytb.malltiny.common.enums.RequestStatusEnum.NO_AUTH;


@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 身份认证不通过异常
     *
     * @param request       that resulted in an <code>AuthenticationException</code>
     * @param response      so that the user agent can begin authentication
     * @param authException that caused the invocation
     * @throws IOException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(CommonResult.result(ERROR.getIsSuccessful(), NO_AUTH.getMessage(), null, NO_AUTH.getCode(), ERROR.getType())));
        response.getWriter().flush();
    }
}