package my.asoul.chat_server.utils;


import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import lombok.Data;
import my.asoul.takeout.model.base.BaseUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author 4512
 * @date 2022/9/26 4:30
 */
@Component
@Data
public class TokenUtil {

    private static String tokenPrefix = "bearer";



    public static BaseUser analysisToken(String token) {
        token = token.replace(tokenPrefix, "");
        JWT jwt = JWT.of(token);
        BaseUser user = new BaseUser();
        user.setId(Long.valueOf(jwt.getPayload("id").toString()));
        user.setAccount((String) jwt.getPayload("account"));
        user.setName((String) jwt.getPayload("name"));
        return user;
    }
}
