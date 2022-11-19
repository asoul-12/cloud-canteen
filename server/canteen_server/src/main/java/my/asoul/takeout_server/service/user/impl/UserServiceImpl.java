package my.asoul.takeout_server.service.user.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.base.BaseUser;
import my.asoul.takeout.model.base.MiniProgramAuth;
import my.asoul.takeout.model.canteen.Canteen;
import my.asoul.takeout.model.user.User;
import my.asoul.takeout.model.user.vo.UserVO;
import my.asoul.takeout_server.mapper.canteen.CanteenMapper;
import my.asoul.takeout_server.mapper.user.UserMapper;
import my.asoul.takeout_server.service.user.UserService;
import my.asoul.takeout_server.util.BaseContext;
import my.asoul.takeout_server.util.MailUtil;
import my.asoul.takeout_server.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author 4512
 * @date 2022/10/3 14:02
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Value("${takeout.token.expireTime}")
    private Long expireTime;

    @Value("${takeout.app.appid}")
    private String appid;

    @Value("${takeout.app.secret}")
    private String secret;

    private final String GRANT_TYPE = "authorization_code";

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CanteenMapper canteenMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Override
    public BaseResponse<String> login(BaseUser baseUser) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        String password = DigestUtils.md5DigestAsHex(baseUser.getPassword().getBytes());
        String account = baseUser.getAccount();
        wrapper.eq(StringUtils.hasText(password), User::getPassword, password);
        wrapper.eq(StringUtils.hasText(account), User::getAccount, account);
        try {
            User user = userMapper.selectOne(wrapper);
            String token = TokenUtil.createToken(user);
            redisTemplate.opsForValue().set(token, "admin", expireTime, TimeUnit.MILLISECONDS);
            return BaseResponse.success(token);
        } catch (RuntimeException e) {
            log.error(e.getCause().toString());
            return BaseResponse.error("login error");
        }
    }

    @Override
    public BaseResponse<String> register(BaseUser baseUser) {

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, baseUser.getAccount());
        User user = userMapper.selectOne(wrapper);
        if (Objects.isNull(user)) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            user = new User();
            user.setName(baseUser.getName());
            user.setAccount(baseUser.getAccount());
            user.setPassword(DigestUtils.md5DigestAsHex(baseUser.getPassword().getBytes()));
            long canteenId = IdUtil.getSnowflakeNextId();
            user.setCanteenId(canteenId);
            // send Email
            mailUtil.sendMail(uuid, baseUser.getAccount());
            String json = JSONUtil.toJsonStr(user);
            redisTemplate.opsForValue().set(uuid, json, 10, TimeUnit.MINUTES);
            return BaseResponse.success("success");
        } else {
            return BaseResponse.error("account is exist");
        }
    }

    @Override
    public BaseResponse<String> active(String id) {
        String json = redisTemplate.opsForValue().get(id);
        int insert = -1;
        if (!Objects.isNull(json)) {
            User user = JSONUtil.toBean(json, User.class);
            insert = userMapper.insert(user);
            redisTemplate.delete(id);
            // canteen
            Canteen canteen = new Canteen()
                    .setId(user.getCanteenId())
                    .setName(user.getName())
                    .setHostId(user.getId());
            canteenMapper.insert(canteen);
        }
        return insert == 1 ? BaseResponse.success("success") : BaseResponse.error("invalid");
    }

    @Override
    public BaseResponse<UserVO> getUserInfo() {
        Long userId = BaseContext.get();
        User user = userMapper.selectById(userId);
        if (Objects.isNull(user)) {
            return BaseResponse.error("userInfo is non-existent");
        }
        Long canteenId = user.getCanteenId();
        Canteen canteen = canteenMapper.selectById(canteenId);

        Assert.notNull(canteen, "canteen is non-existent");
        UserVO userVO = Convert.convert(UserVO.class, user);
        userVO.setCanteenName(canteen.getName())
                .setCanteenId(canteenId);
        return BaseResponse.success(userVO);
    }

    @Override
    public BaseResponse<UserVO> editUserInfo(User user) {
        user.setId(BaseContext.get());
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        return userMapper.updateUserInfo(user) == 1 ? BaseResponse.success("edit success!", null) : BaseResponse.error("edit fail");
    }

    @Override
    public BaseResponse<MiniProgramAuth> auth(String code, String nickName, String avatarUrl) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + secret + "&js_code=" + code + "&grant_type=" + GRANT_TYPE;
        String data = HttpUtil.get(url);
        String sessionKey = JSONUtil.parse(data).getByPath("session_key").toString();
        String openid = JSONUtil.parse(data).getByPath("openid").toString();

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getAccount, openid);
        User user = userMapper.selectOne(wrapper);
        if (user == null) {
            user = new User();
            long id = IdUtil.getSnowflake().nextId();
            user.setAccount(openid);
            user.setId(id);
            user.setAvatar(avatarUrl);
            user.setName(nickName);
            userMapper.insert(user);
        }
        String token = TokenUtil.createToken(user);
        redisTemplate.opsForValue().set(token, sessionKey);
        return BaseResponse.success(new MiniProgramAuth(user.getId(), token));
    }


}
