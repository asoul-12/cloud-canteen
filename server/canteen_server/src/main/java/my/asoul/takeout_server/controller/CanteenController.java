package my.asoul.takeout_server.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.canteen.Canteen;
import my.asoul.takeout.model.dish.Category;
import my.asoul.takeout.model.dish.Dish;
import my.asoul.takeout_server.service.canteen.CanteenService;
import my.asoul.takeout_server.service.dish.CategoryService;
import my.asoul.takeout_server.service.dish.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 4512
 * @date 2022/11/1 22:45
 */
@RestController
@RequestMapping("/canteen")
public class CanteenController {

    @Autowired
    private CanteenService canteenService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DishService dishService;

    /**
     * 小程序获取餐厅list
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping("/list/{currentPage}/{pageSize}")
    public BaseResponse<Page<Canteen>> getCanteenList(@PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize) {
        return canteenService.getCanteenList(currentPage, pageSize);
    }

    /**
     * 小程序获取菜品分类
     *
     * @return
     */
    @GetMapping("/category/{canteenId}")
    public BaseResponse<List<Category>> getCategoryList(@PathVariable("canteenId") long canteenId) {
        return categoryService.categoryList(canteenId);
    }

    /**
     * 小程序根据categoryId获取菜品
     * @param categoryId
     * @return
     */
    @GetMapping("/dish/{categoryId}")
    public BaseResponse<List<Dish>> getDishList(@PathVariable("categoryId") long categoryId) {
        return dishService.listDishByCategory(categoryId);
    }

}
