package my.asoul.takeout_server.controller;

import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.base.MiniProgramAuth;
import my.asoul.takeout_server.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 4512
 * @date 2022/11/3 16:48
 * 小程序用户
 */
@RestController
@RequestMapping("/userAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public BaseResponse<MiniProgramAuth> userLogin(@RequestParam String code , @RequestParam String nickName, @RequestParam String avatarUrl) {
        return userService.auth(code,nickName,avatarUrl);
    }

}
