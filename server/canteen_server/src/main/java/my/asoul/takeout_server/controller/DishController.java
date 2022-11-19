package my.asoul.takeout_server.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.dish.Dish;
import my.asoul.takeout_server.service.dish.DishService;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author 4512
 * @date 2022/9/15 19:51
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    /**
     * 条件分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页显示数量
     * @return
     */
    @GetMapping("/{canteenId}/{currentPage}/{pageSize}/{condition}")
    public BaseResponse<Page<Dish>> pageByQuery(@PathVariable("canteenId") long canteenId, @PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize, @PathVariable("condition") String condition) {
        return dishService.page(canteenId,currentPage, pageSize, condition);
    }

    /**
     * 分页
     *
     * @param currentPage 当前页
     * @param pageSize    每页显示数量
     * @return
     */
    @GetMapping("/{canteenId}/{currentPage}/{pageSize}")
    public BaseResponse<Page<Dish>> page(@PathVariable("canteenId") long canteenId, @PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize) {
        return dishService.page(canteenId,currentPage, pageSize, null);
    }


    /**
     * batchDel
     *
     * @param ids
     * @return
     */
    @CacheEvict(value = "dish", allEntries = true)
    @PutMapping("/batch-delete")
    public BaseResponse<String> batchDelete(@RequestBody ArrayList<String> ids) {
        return dishService.removeBatchByIds(ids) ? BaseResponse.success("delete!") : BaseResponse.error("delete fail");
    }

    /**
     * 批量上架\下架
     *
     * @param dishes 菜品
     * @param status 状态值
     * @return
     */
    @CacheEvict(value = "dish", allEntries = true)
    @PutMapping("/{status}")
    public BaseResponse<String> updateStatus(@RequestBody Dish[] dishes, @PathVariable @Range(min = 0, max = 1) Integer status) {
        return dishService.batchUpdate(dishes, status);
    }

    /**
     * 更新菜品信息
     *
     * @param dish 菜品
     * @return
     */
    @CacheEvict(value = "dish", key = "#dish.categoryId")
    @PutMapping
    public BaseResponse<String> update(@RequestBody @Validated Dish dish) {
        return dishService.updateById(dish) ? BaseResponse.success("success") : BaseResponse.error("fail");
    }


    /**
     * 添加菜品到数据库
     *
     * @param dish 菜品
     * @return
     */
    @CacheEvict(value = "dish", key = "#dish.categoryId")
    @PostMapping
    public BaseResponse<String> save(@RequestBody @Validated Dish dish) {
        return dishService.save(dish) ? BaseResponse.success("success") : BaseResponse.error("fail");
    }

    // ------------------------------------

    /**
     * 根据id获取菜品
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public BaseResponse<Dish> getById(@PathVariable Long id) {
        Dish dish = dishService.getById(id);
        return Objects.isNull(dish) ? BaseResponse.error("dish is nonExist") : BaseResponse.success(dish);
    }

    /**
     * 根据分类id获取该分类下的所有菜品
     *
     * @param categoryId 分类id
     * @return
     */
    @GetMapping("/list/{categoryId}")
    public BaseResponse<List<Dish>> listDishByCategory(@PathVariable Long categoryId) {
        return dishService.listDishByCategory(categoryId);
    }
}
