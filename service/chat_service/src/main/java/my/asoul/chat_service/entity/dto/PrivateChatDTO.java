package my.asoul.chat_service.entity.dto;

import lombok.Data;
import my.asoul.takeout.model.user.User;

/**
 * @author 4512
 * @date 2022/11/5 1:35
 */
@Data
public class PrivateChatDTO {
    private User user;
    private Long sessionId;
    private Integer unreadCount;
}
