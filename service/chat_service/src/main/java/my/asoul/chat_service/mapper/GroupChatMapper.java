package my.asoul.chat_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.asoul.chat_service.entity.GroupChat;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 4512
 * @date 2022/10/9 20:28
 */
@Mapper
public interface GroupChatMapper extends BaseMapper<GroupChat> {

    /**
     * 根据当前用户id获取
     * @param userId
     * @return
     */
    GroupChat getGroupChat(long userId);
}
