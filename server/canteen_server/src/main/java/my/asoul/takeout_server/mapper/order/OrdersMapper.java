package my.asoul.takeout_server.mapper.order;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import my.asoul.takeout.model.base.PageInfo;
import my.asoul.takeout.model.order.OrderDTO;
import my.asoul.takeout.model.order.Orders;
import my.asoul.takeout.model.order.OrdersBackendDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

/**
 * @author 4512
 * @date 2022/9/20 2:37
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {

    /**
     * 后台获取订单分页
     *
     * @param page 分页插件
     * @return 订单分页信息
     */
    Page<OrdersBackendDTO> ordersList(Page<OrdersBackendDTO> page, Wrapper<OrdersBackendDTO> wrapper);

    /**
     * 小程序获取订单分页
     *
     * @param pageInfo
     * @return
     */
    ArrayList<OrderDTO> getOrderListPage(PageInfo pageInfo);

    /**
     * 修改订单状态
     */

}
