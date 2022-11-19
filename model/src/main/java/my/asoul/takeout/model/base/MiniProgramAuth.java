package my.asoul.takeout.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 4512
 * @date 2022/11/5 7:50
 */
@Data
@AllArgsConstructor
public class MiniProgramAuth {
    private Long userId;
    private String token;
}
