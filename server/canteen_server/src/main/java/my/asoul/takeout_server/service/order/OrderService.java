package my.asoul.takeout_server.service.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.order.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author 4512
 * @date 2022/9/20 2:30
 */
public interface OrderService extends IService<Orders> {

    /**
     * 小程序用户订单分页
     *
     * @param currentPage 当前页
     * @param pageSize    一页显示内容
     * @return page
     */
    BaseResponse<ArrayList<OrderDTO>> page(long id, int currentPage, int pageSize);

    /**
     * 后台订单分页
     *
     * @param orderPageDTO 分页数据
     * @return 订单分页
     */
    BaseResponse<Page<OrdersBackendDTO>> backendPage(OrderPageDTO orderPageDTO);

    /**
     * 小程序创建订单
     *
     * @return
     */
    BaseResponse<Long> createOrder(LinkedHashMap<Long, OrderDetail> map, long canteenId);

    /**
     * 返回订单金额
     *
     * @param orderId
     * @return
     */
    BaseResponse<BigDecimal> checkAmount(long orderId);

    /**
     * 订单付款
     *
     * @param orderId
     * @return
     */
    BaseResponse<String> payOrder(long orderId);
}
