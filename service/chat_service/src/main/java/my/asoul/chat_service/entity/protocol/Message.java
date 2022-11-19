package my.asoul.chat_service.entity.protocol;

import lombok.Data;
import lombok.experimental.Accessors;
import my.asoul.takeout.model.user.User;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 4512
 * @date 2022/10/2 14:44
 */
@Data
@Accessors(chain = true)
public class Message implements Serializable {
    private Long id;
    private User user;
    private Long receiveUserId;
    private String content;
    private Long sessionId;
    private LocalDateTime sendTime;
}
