package my.asoul.takeout_server.mapper.order;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.asoul.takeout.model.order.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 4512
 * @date 2022/9/20 3:09
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
