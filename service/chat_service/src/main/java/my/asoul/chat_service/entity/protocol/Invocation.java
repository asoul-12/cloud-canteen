package my.asoul.chat_service.entity.protocol;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author 4512
 * @date 2022/10/9 11:04
 */
@Data
@NoArgsConstructor
public class Invocation implements Serializable {

    private Message message;
    private String type;

    public Invocation(String type, Message message) {
        this.type = type;
        this.message = message;
    }

}
