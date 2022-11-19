package my.asoul.chat_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.asoul.chat_service.entity.PrivateChatRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 4512
 * @date 2022/10/12 18:15
 */
@Mapper
public interface PrivateChatRecordMapper extends BaseMapper<PrivateChatRecord> {
}
