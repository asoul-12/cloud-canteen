package my.asoul.takeout_server.service.dish.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.dish.Category;
import my.asoul.takeout.model.dish.Dish;
import my.asoul.takeout.model.dish.DishVO;
import my.asoul.takeout_server.mapper.dish.CategoryMapper;
import my.asoul.takeout_server.mapper.dish.DishMapper;
import my.asoul.takeout_server.service.dish.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 4512
 * @date 2022/9/15 19:53
 */
@Service
@SuppressWarnings("all")
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    final Integer ON_SALE = 1;
    final Integer FORBID_SALE = 0;

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public BaseResponse<Page<Dish>> page(long canteenId, int currentSize, int pageSize, String condition) {
        Page<Dish> page = new Page<>(currentSize, pageSize);
        return BaseResponse.success(dishMapper.listDishes(page, condition, canteenId));
    }

    @Override
    public BaseResponse<String> batchUpdate(Dish[] dishes, Integer status) {
        for (Dish dish : dishes) {
            DishVO dishVO = Convert.convert(DishVO.class, dish).setStatus(status);
            dishMapper.updateStatus(dishVO);
        }
        return BaseResponse.success("success");
    }

    @Override
    @Cacheable(value = "dish", key = "#categoryId")
    public BaseResponse<List<Dish>> listDishByCategory(Long categoryId) {
        QueryWrapper<Dish> dishWrapper = new QueryWrapper<>();
        dishWrapper.eq("status", ON_SALE);
        dishWrapper.eq("category_id", categoryId);
        List<Dish> dishes = dishMapper.selectList(dishWrapper);
        return dishes.size() > 0 ? BaseResponse.success(dishes) : BaseResponse.error("fail");
    }

    @Override
    public int updateDishAvatar(long id, String fileName) {
        Dish dish = new Dish().setId(id).setImage("https://canteen-image-1255913523.cos.ap-guangzhou.myqcloud.com/dish-avatar/" + fileName);
        return dishMapper.updateById(dish);
    }
}
