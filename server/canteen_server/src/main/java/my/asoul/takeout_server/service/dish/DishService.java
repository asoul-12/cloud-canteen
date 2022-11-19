package my.asoul.takeout_server.service.dish;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.dish.Dish;

import java.util.List;

/**
 * @author 4512
 * @date 2022/9/15 19:52
 */
public interface DishService extends IService<Dish> {


    /**
     * 菜品分页
     *
     * @param currentSize 当前页
     * @param pageSize
     * @return
     */
    BaseResponse<Page<Dish>> page(long canteenId, int currentSize, int pageSize, String condition);


    /**
     * 批量修改菜品的状态
     *
     * @param dishes 菜品
     * @param status 菜品状态 禁售/在售
     * @return
     */
    BaseResponse<String> batchUpdate(Dish[] dishes, Integer status);

    /**
     * 根据菜品类别id查找相应菜品
     *
     * @param categoryId 菜品类别id
     */
    BaseResponse<List<Dish>> listDishByCategory(Long categoryId);


    /**
     * 更新菜品图片
     * @param id
     * @param fileName
     * @return
     */
    int updateDishAvatar(long id, String fileName);
}
