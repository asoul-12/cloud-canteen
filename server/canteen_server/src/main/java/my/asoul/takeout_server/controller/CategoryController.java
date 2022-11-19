package my.asoul.takeout_server.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.dish.Category;
import my.asoul.takeout_server.service.dish.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 4512
 * @date 2022/9/15 14:59
 */
@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 添加菜品分类
     *
     * @param category
     * @return
     */
    @PostMapping
    @CacheEvict(value = "categoryList",key = "#category.canteenId")
    public BaseResponse<String> save(@RequestBody @Validated Category category) {
        return categoryService.save(category) ? BaseResponse.success("save!") : BaseResponse.error("save fail");
    }

    /**
     * 分页
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/{canteenId}/{currentPage}/{pageSize}")
    public BaseResponse<Page<Category>> page(@PathVariable("canteenId") long canteenId, @PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize) {
        return categoryService.page(canteenId, currentPage, pageSize, null);
    }

    /**
     * 条件分页
     *
     * @param currentPage
     * @param pageSize
     * @param condition
     * @return
     */
    @GetMapping("/{canteenId}/{currentPage}/{pageSize}/{condition}")
    public BaseResponse<Page<Category>> pageByQuery(@PathVariable("canteenId") long canteenId, @PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize, @PathVariable("condition") String condition) {
        return categoryService.page(canteenId, currentPage, pageSize, condition);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PutMapping("/batch-delete")
    @CacheEvict(value = "categoryList",allEntries = true)
    public BaseResponse<String> batchDelete(@RequestBody ArrayList<String> ids) {
        return categoryService.removeBatchByIds(ids) ? BaseResponse.success("delete!") : BaseResponse.error("delete fail");
    }

    /**
     * 更新菜品分类
     *
     * @param category
     * @return
     */
    @PutMapping
    @CacheEvict(value = "categoryList",key = "#category.canteenId")
    public BaseResponse<String> update(@RequestBody @Validated Category category) {
        return categoryService.updateById(category) ? BaseResponse.success("update!") : BaseResponse.error("update fail");
    }


    /**
     * 商家获取菜品分类
     *
     * @param canteenId
     * @return
     */
    @GetMapping("/list/{canteenId}")
    public BaseResponse<List<Category>> getCategoryList(@PathVariable("canteenId") long canteenId) {
        return categoryService.categoryList(canteenId);
    }

}
