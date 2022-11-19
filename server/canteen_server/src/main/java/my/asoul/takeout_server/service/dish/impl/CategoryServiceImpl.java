package my.asoul.takeout_server.service.dish.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.dish.Category;
import my.asoul.takeout_server.mapper.dish.CategoryMapper;
import my.asoul.takeout_server.service.dish.CategoryService;
import my.asoul.takeout_server.util.BaseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 4512
 * @date 2022/9/13 22:28
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public BaseResponse<Page<Category>> page(long canteenId,int currentPage, int pageSize, String condition) {
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        if (condition != null) {
            lqw.like(Category::getName, condition)
                    .or(true)
                    .like(Category::getSort, condition);
        }
        lqw.eq(Category::getCanteenId, canteenId);
        lqw.orderByAsc(Category::getSort);

        Page<Category> page = new Page<>(currentPage, pageSize);
        return BaseResponse.success(categoryMapper.selectPage(page, lqw));
    }

    @Override
    @Cacheable(value = "categoryList",key = "#canteenId")
    public BaseResponse<List<Category>> categoryList(long canteenId) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getCanteenId, canteenId);
        List<Category> list = categoryMapper.selectList(wrapper);
        return list.size() > 0 ? BaseResponse.success(list) : BaseResponse.error("categoryList is empty");
    }
}
