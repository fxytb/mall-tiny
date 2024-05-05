package com.fxytb.malltiny.controller;

import cn.hutool.core.util.StrUtil;
import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.model.param.UserAdminLoginParam;
import com.fxytb.malltiny.sevice.UserAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/umsAdmin")
@Api(tags = "UmsAdminController")
@Tag(name = "UmsAdminController", description = "用户控制类")
public class UmsAdminController {

    @Autowired
    private UserAdminService userAdminService;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 用户登录
     *
     * @param loginParam
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "401", description = "权限校验未通过"),
                    @ApiResponse(responseCode = "403", description = "访问被拒绝")
            }
    )
    public CommonResult<Object> login(@RequestBody @Validated UserAdminLoginParam loginParam) {
        String token = userAdminService.login(loginParam);
        if (StrUtil.isNotBlank(token)) {
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            return CommonResult.success("用户登录成功", tokenMap);
        } else {
            return CommonResult.error("用户登录失败,账号或密码不正确");
        }
    }


}
