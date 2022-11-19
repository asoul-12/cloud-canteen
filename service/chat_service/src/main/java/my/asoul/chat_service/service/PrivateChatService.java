package my.asoul.chat_service.service;

import my.asoul.chat_service.entity.PrivateChatRecord;
import my.asoul.chat_service.entity.dto.PrivateChatDTO;
import my.asoul.chat_service.entity.protocol.Invocation;
import my.asoul.takeout.model.base.BaseResponse;

import java.util.List;

/**
 * @author 4512
 * @date 2022/10/9 20:37
 */
public interface PrivateChatService {


    /**
     * 拉取聊天列表
     *
     * @return
     */
    BaseResponse<List<PrivateChatDTO>> getChatList(long userId);

    /**
     * 拉取聊天记录
     *
     * @param sessionId 会话id
     */
    BaseResponse<List<PrivateChatRecord>> getChatRecord(long sessionId);

    /**
     * 聊天记录落地
     */
    int saveChatRecord(Invocation invocation);
}
