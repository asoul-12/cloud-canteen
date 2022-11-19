package my.asoul.chat_service.service.impl;

import cn.hutool.core.util.IdUtil;
import my.asoul.chat_service.entity.PrivateChatRecord;
import my.asoul.chat_service.entity.dto.PrivateChatDTO;
import my.asoul.chat_service.entity.protocol.Invocation;
import my.asoul.chat_service.entity.protocol.Message;
import my.asoul.chat_service.entity.protocol.MessageType;
import my.asoul.chat_service.mapper.PrivateChatMapper;
import my.asoul.chat_service.service.PrivateChatService;
import my.asoul.takeout.model.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 4512
 * @date 2022/10/9 20:37
 */
@Service
public class PrivateChatServiceImpl implements PrivateChatService {


    @Autowired
    private PrivateChatMapper privateChatMapper;

    @Override
    public BaseResponse<List<PrivateChatDTO>> getChatList(long userId) {
        ArrayList<PrivateChatDTO> chatList = privateChatMapper.getChatListById(userId);
        return BaseResponse.success(chatList);
    }

    @Override
    public BaseResponse<List<PrivateChatRecord>> getChatRecord(long sessionId) {
        ArrayList<PrivateChatRecord> chatRecord = privateChatMapper.getChatRecord(sessionId);
        return BaseResponse.success(chatRecord);
    }

    @Override
    public int saveChatRecord(Invocation invocation) {
        Message message = invocation.getMessage();
        if (MessageType.NEW_PRIVATE_CHAT.getMessageType().equals(invocation.getType())) {
            privateChatMapper.savePrivateChat(message);

        } else {
            privateChatMapper.updatePrivateChatUnreadCount(message);
        }
        return privateChatMapper.savePrivateChatRecord(message);
    }



}
