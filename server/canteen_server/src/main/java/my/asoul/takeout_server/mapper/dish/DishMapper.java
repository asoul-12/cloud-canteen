package my.asoul.takeout_server.mapper.dish;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import my.asoul.takeout.model.dish.Dish;
import my.asoul.takeout.model.dish.DishVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author 4512
 * @date 2022/9/15 19:54
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {


    /**
     * 获取dish表分页结果
     *
     * @param page 分页器
     * @param condition 分页器
     * @param canteenId 分页器
     * @return
     */
    Page<Dish> listDishes(Page<Dish> page, @Param("condition") String condition, @Param("canteenId") Long canteenId);


    Boolean updateStatus(DishVO dishVO);


}
