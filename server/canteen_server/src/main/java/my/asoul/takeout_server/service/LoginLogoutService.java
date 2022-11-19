package my.asoul.takeout_server.service;

import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.base.BaseUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 4512
 * @date 2022/9/27 2:50
 */
public interface LoginLogoutService {


    BaseResponse<String> login(BaseUser user);


    /**
     * 登出
     *
     * @param request 请求
     * @return
     */
    default BaseResponse<String> logout(HttpServletRequest request) {
        return null;
    }

}
