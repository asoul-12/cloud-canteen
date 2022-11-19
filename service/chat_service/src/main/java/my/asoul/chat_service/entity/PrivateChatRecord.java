package my.asoul.chat_service.entity;

import lombok.Data;
import lombok.experimental.Accessors;
import my.asoul.chat_service.entity.protocol.Message;

/**
 * @author 4512
 * @date 2022/10/9 20:19
 */
@Data
@Accessors(chain = true)
public class PrivateChatRecord extends Message {
}


