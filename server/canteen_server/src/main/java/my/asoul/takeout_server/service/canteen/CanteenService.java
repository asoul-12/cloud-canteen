package my.asoul.takeout_server.service.canteen;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.canteen.Canteen;

/**
 * @author 4512
 * @date 2022/11/1 22:47
 */
public interface CanteenService extends IService<Canteen> {

    /**
     * 获取所有店铺列表
     *
     * @return
     */
    BaseResponse<Page<Canteen>> getCanteenList(int currentPage, int pageSize);

    /**
     * 上传餐厅图片
     */
    int updateCanteenAvatar(long id, String fileName);


}
