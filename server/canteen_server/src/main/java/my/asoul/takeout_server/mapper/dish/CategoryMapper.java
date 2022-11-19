package my.asoul.takeout_server.mapper.dish;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.asoul.takeout.model.dish.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 4512
 * @date 2022/9/15 15:01
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
