package my.asoul.chat_service.controller;

import my.asoul.chat_service.entity.PrivateChatRecord;
import my.asoul.chat_service.entity.dto.PrivateChatDTO;
import my.asoul.chat_service.service.PrivateChatService;
import my.asoul.takeout.model.base.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 4512
 * @date 2022/10/9 20:32
 */
@RestController
@RequestMapping("/chat")
public class ChatController {


    @Autowired
    private PrivateChatService privateChatService;

    /**
     * 拉取指定用户的所有聊天list
     *
     * @return
     */
    @GetMapping("/get-chat-list/{userId}")
    public BaseResponse<List<PrivateChatDTO>> getChatList(@PathVariable long userId) {
        return privateChatService.getChatList(userId);
    }

    /**
     * 拉取当前会话的聊天记录
     * @param sessionId
     * @return
     */
    @GetMapping("/get-chat-record/{sessionId}")
    public BaseResponse<List<PrivateChatRecord>> getCharRecord(@PathVariable long sessionId) {
        return privateChatService.getChatRecord(sessionId);
    }

}
