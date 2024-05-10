package com.fxytb.malltiny.component;

import cn.hutool.json.JSONUtil;
import com.fxytb.malltiny.common.result.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.fxytb.malltiny.common.enums.MessageEnum.ERROR;
import static com.fxytb.malltiny.common.enums.RequestStatusEnum.FORBIDDEN;


@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 权限不足异常
     *
     * @param request  that resulted in an <code>AccessDeniedException</code>
     * @param response so that the user agent can be advised of the failure
     * @param e        that caused the invocation
     * @throws IOException
     */
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().println(JSONUtil.parse(
                CommonResult.result(
                        ERROR.getIsSuccessful(),
                        FORBIDDEN.getMessage(),
                        null,
                        FORBIDDEN.getCode(),
                        ERROR.getType())
        ));
        response.getWriter().flush();
    }
}