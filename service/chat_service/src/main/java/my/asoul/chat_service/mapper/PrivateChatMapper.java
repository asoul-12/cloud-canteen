package my.asoul.chat_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.asoul.chat_service.entity.PrivateChat;
import my.asoul.chat_service.entity.PrivateChatRecord;
import my.asoul.chat_service.entity.dto.PrivateChatDTO;
import my.asoul.chat_service.entity.protocol.Message;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @author 4512
 * @date 2022/10/9 20:50
 */
@Mapper
public interface PrivateChatMapper extends BaseMapper<PrivateChat> {

    /**
     * 根据userId获取聊天列表
     *
     * @param userId
     * @return
     */
    ArrayList<PrivateChatDTO> getChatListById(long userId);

    /**
     * 根据sessionId拉取聊天记录
     *
     * @param sessionId 会话id
     * @return
     */
    ArrayList<PrivateChatRecord> getChatRecord(long sessionId);

    /**
     * private 保存聊天记录
     *
     * @param message
     * @return
     */
    int savePrivateChatRecord(Message message);


    /**
     * 保存聊天session
     *
     * @param message
     * @return
     */
    int savePrivateChat(Message message);

    /**
     * 更新未读信息
     * @param message
     * @return
     */
    int updatePrivateChatUnreadCount(Message message);

}
