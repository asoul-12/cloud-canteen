package my.asoul.chat_service.entity;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author 4512
 * @date 2022/10/9 20:18
 */
@Data
public class GroupChatRecord {
    private Long id;
    private Long toGroupId;
    private Long fromId;
    private String content;
    private LocalDate sendTime;
}
