package my.asoul.chat_service;

import my.asoul.chat_service.entity.dto.PrivateChatDTO;
import my.asoul.chat_service.mapper.PrivateChatMapper;
import my.asoul.chat_service.service.PrivateChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

@SpringBootTest
class ChatServiceApplicationTests {

    @Autowired
    private PrivateChatService service;

    @Autowired
    private PrivateChatMapper privateChatMapper;

    @Test
    void contextLoads() {
        ArrayList<PrivateChatDTO> chatList = privateChatMapper.getChatListById(123L);
        System.out.println(chatList.toString());
    }

}
