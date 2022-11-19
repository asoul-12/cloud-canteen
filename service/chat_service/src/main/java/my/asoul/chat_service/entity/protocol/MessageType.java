package my.asoul.chat_service.entity.protocol;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 4512
 * @date 2022/11/13 1:33
 */
@Getter
@AllArgsConstructor
public enum MessageType {
    /**
     *
     */
    PRIVATE_CHAT("privateChat"),
    NEW_PRIVATE_CHAT("newPrivateChat");
    private final String messageType;
}
