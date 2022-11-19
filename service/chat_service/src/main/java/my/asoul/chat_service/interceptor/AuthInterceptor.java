package my.asoul.chat_service.interceptor;

import my.asoul.chat_service.exception.TokenException;
import my.asoul.chat_service.util.BaseContext;
import my.asoul.chat_service.util.TokenUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 4512
 * @date 2022/10/9 20:42
 */
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (!StringUtils.hasText(token)) {
            throw new TokenException("token error");
        } else if (TokenUtil.validateToken(token)) {
            BaseContext.set(TokenUtil.analysisToken(token).getId());
            return true;
        } else {
            throw new TokenException("token error");
        }
    }
}
