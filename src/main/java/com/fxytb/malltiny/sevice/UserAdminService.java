package com.fxytb.malltiny.sevice;

import com.fxytb.malltiny.common.result.CommonResult;
import com.fxytb.malltiny.model.bo.AdminUserDetails;
import com.fxytb.malltiny.model.param.UserAdminLoginParam;

public interface UserAdminService {
    String login(UserAdminLoginParam loginParam);

    AdminUserDetails getAdminByUsername(String username);
}
