package my.asoul.takeout_server.service.user;

import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.base.BaseUser;
import my.asoul.takeout.model.base.MiniProgramAuth;
import my.asoul.takeout.model.user.User;
import my.asoul.takeout.model.user.vo.UserVO;

/**
 * @author 4512
 * @date 2022/9/18 1:44
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param user
     * @return
     */
    BaseResponse<String> login(BaseUser user);

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    BaseResponse<String> register(BaseUser user);


    /**
     * 注册激活
     *
     * @param id 激活链接附带的id
     * @return
     */
    BaseResponse<String> active(String id);

    /**
     * 获取商家个人信息
     * @return
     */
    BaseResponse<UserVO> getUserInfo();

    /**
     * 修改商家个人信息
     * @param user
     * @return
     */
    BaseResponse<UserVO> editUserInfo(User user);

    /**
     * 小程序用户鉴权
     * @param code     用户唯一code
     * @param nickName 用户nickName
     * @param avatarUrl 用户头像url
     * @return userId & token
     */
    BaseResponse<MiniProgramAuth> auth(String code, String nickName, String avatarUrl);
}
