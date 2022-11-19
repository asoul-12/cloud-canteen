package my.asoul.chat_service.entity;

import lombok.Data;

/**
 * @author 4512
 * @date 2022/10/9 20:20
 */
@Data
public class PrivateChat {
    private Long id;
    private Long sendUserId;
    private Long receiveUserId;
    private Long sessionId;
    private Integer unreadCount;
}
