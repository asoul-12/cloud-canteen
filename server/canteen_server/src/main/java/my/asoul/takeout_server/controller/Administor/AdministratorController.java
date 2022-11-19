package my.asoul.takeout_server.controller.Administor;

import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.user.User;
import my.asoul.takeout.model.user.vo.UserVO;
import my.asoul.takeout_server.service.user.impl.UserServiceImpl;
import my.asoul.takeout_server.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 4512
 * @date 2022/10/3 13:50
 */
@RestController
@RequestMapping("/admin")
public class AdministratorController {

    @Autowired
    private UserServiceImpl administratorService;

    @PostMapping("/login")
    public BaseResponse<String> login(@RequestBody @Validated User user) {
        return administratorService.login(user);
    }

    @PostMapping("/logout")
    public BaseResponse<String> logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (!"".equals(token) && token != null) {
            TokenUtil.invalidToken(token);
        }
        return BaseResponse.success("logout");
    }


    @PostMapping("/register")
    public BaseResponse<String> register(@RequestBody @Validated User user) {
        return administratorService.register(user);
    }

    /**
     * 激活账户
     * @param id
     * @param response
     * @return
     * @throws IOException
     */
    @GetMapping("/active/{id}")
    public BaseResponse<String> active(@PathVariable String id, HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:3000/#/login");
        return administratorService.active(id);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    @PostMapping("/info")
    public BaseResponse<UserVO> getUserInfo() {
        return administratorService.getUserInfo();
    }

    /**
     * 修改用户信息
     *
     * @param user)
     * @return
     */
    @PutMapping("/info")
    public BaseResponse<UserVO> editUserInfo(@RequestBody User user) {
        return administratorService.editUserInfo(user);
    }

}
