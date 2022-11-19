package my.asoul.chat_service.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 4512
 * @date 2022/10/3 14:37
 */
@Data
@AllArgsConstructor
public class CommonException extends RuntimeException {
    private String msg;
}
