package my.asoul.chat_service.entity.dto;

import lombok.Data;
import my.asoul.chat_service.entity.PrivateChatRecord;

import java.util.ArrayList;

/**
 * @author 4512
 * @date 2022/10/10 15:35
 */
@Data
public class PrivateChatRecordDTO {
    private Long sessionId;
    private Long toId;
    private String username;
    private ArrayList<PrivateChatRecord> recordList;

}
