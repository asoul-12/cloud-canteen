package my.asoul.chat_server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 4512
 * @date 2022/10/8 20:26
 */
@Data
@AllArgsConstructor
public class ChatResponse<T> implements Serializable {
    private String msg;
    private String type;
    private T data;
}
