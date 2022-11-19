package my.asoul.takeout_server.service.dish;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.dish.Category;

import java.util.List;

/**
 * @author 4512
 * @date 2022/9/13 22:27
 */
public interface CategoryService extends IService<Category> {


    /**
     * 分类分页
     *
     * @param currentPage 当前页
     * @param pageSize    页面内容数量
     * @param condition   条件查询
     * @return
     */
    BaseResponse<Page<Category>> page(long canteenId,int currentPage, int pageSize, String condition);

    /**
     * 获取所有菜品分类
     *
     * @return
     */
    BaseResponse<List<Category>> categoryList(long canteenId);
}
