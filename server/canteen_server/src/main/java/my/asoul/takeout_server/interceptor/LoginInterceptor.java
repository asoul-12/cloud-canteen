package my.asoul.takeout_server.interceptor;


import lombok.extern.slf4j.Slf4j;
import my.asoul.takeout.model.base.BaseUser;
import my.asoul.takeout_server.util.BaseContext;
import my.asoul.takeout_server.util.TokenUtil;
import my.asoul.takeout_server.exception.TokenException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author 4512
 * @date 2022/9/14 1:25
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println(request.getRequestURL());
        String token = request.getHeader("Authorization");
        if ("".equals(token) || Objects.isNull(token)) {
            throw new TokenException("token is null");
        } else if (TokenUtil.validateToken(token)) {
            // 将token解析并将用户信息放入threadLocal
            BaseUser baseUser = TokenUtil.analysisToken(token);
            BaseContext.set(baseUser.getId());
            return true;
        } else {
            throw new TokenException("token is invalid");
        }
    }
}
