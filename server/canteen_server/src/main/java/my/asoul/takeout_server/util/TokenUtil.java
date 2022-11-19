package my.asoul.takeout_server.util;


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

    private static String tokenPrefix;

    @Value("${takeout.token.prefix}")
    public void setTokenPrefix(String tokenPrefix) {
        TokenUtil.tokenPrefix = tokenPrefix;
    }

    private static String key;

    @Value("${takeout.token.secret}")
    public void setKey(String key) {
        TokenUtil.key = key;
    }

    private static Long expireTime;

    @Value("${takeout.token.expireTime}")
    public void setExpireTime(Long expireTime) {
        TokenUtil.expireTime = expireTime;
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate0;

    private static RedisTemplate<String, String> redisTemplate;

    @PostConstruct
    private void initRedisTemplate() {
        redisTemplate = this.redisTemplate0;
    }


    /**
     * 根据用户信息创建token
     *
     * @param user 用户信息
     * @return token
     */
    public static String createToken(BaseUser user) {
        return tokenPrefix + JWT.create()
                .setPayload("id", user.getId())
                .setPayload("account", user.getAccount())
                .setPayload("name", user.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .setKey(key.getBytes()).sign();
    }

    /**
     * 验证用户token
     *
     * @param token 用户token
     * @return 是否通过
     */
    public static Boolean validateToken(String token) {
        if (redisTemplate.opsForValue().get(token) == null) {
            return false;
        }
        if (token.startsWith(tokenPrefix)) {
            token = token.replace(tokenPrefix, "");
        } else {
            return false;
        }

        return JWT.of(token).setKey(key.getBytes()).validate(0);
    }


    public static void invalidToken(String token) {
        token = token.replace(tokenPrefix, "");
        JWT jwt = JWT.of(token);
        Date date = jwt.getPayload().getClaimsJson().getDate(JWTPayload.EXPIRES_AT);
        // 获取剩余时间
        long remainingTime = date.getTime() - System.currentTimeMillis();
        redisTemplate.opsForValue().set("tokenBlacklist", token, remainingTime, TimeUnit.MILLISECONDS);
    }

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
