package my.asoul.takeout_server.mapper.user;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.asoul.takeout.model.user.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 4512
 * @date 2022/9/18 1:45
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUserInfo(User user);
}
