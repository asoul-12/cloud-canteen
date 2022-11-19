package my.asoul.takeout_server.service.canteen.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.canteen.Canteen;
import my.asoul.takeout_server.mapper.canteen.CanteenMapper;
import my.asoul.takeout_server.service.canteen.CanteenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author 4512
 * @date 2022/11/1 22:58
 */
@Service
public class CanteenServiceImpl extends ServiceImpl<CanteenMapper, Canteen> implements CanteenService {

    @Autowired
    private CanteenMapper canteenMapper;

    @Override
    @Cacheable(value = "canteenList", key = "#currentPage+#pageSize")
    public BaseResponse<Page<Canteen>> getCanteenList(int currentPage, int pageSize) {
        Page<Canteen> page = new Page<>(currentPage, pageSize);
        Page<Canteen> canteenPage = canteenMapper.selectPage(page, null);
        return BaseResponse.success(canteenPage);
    }

    @Override
    public int updateCanteenAvatar(long id, String fileName) {
        Canteen canteen = new Canteen().setId(id).setAvatar("https://canteen-image-1255913523.cos.ap-guangzhou.myqcloud.com/canteen-avatar/" + fileName);
        return canteenMapper.updateById(canteen);
    }
}
