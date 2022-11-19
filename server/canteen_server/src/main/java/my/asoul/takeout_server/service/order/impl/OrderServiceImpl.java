package my.asoul.takeout_server.service.order.impl;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.base.PageInfo;
import my.asoul.takeout.model.dish.Dish;
import my.asoul.takeout.model.order.*;
import my.asoul.takeout_server.mapper.dish.DishMapper;
import my.asoul.takeout_server.mapper.order.OrderDetailMapper;
import my.asoul.takeout_server.util.BaseContext;
import my.asoul.takeout_server.mapper.order.OrdersMapper;
import my.asoul.takeout_server.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author hjy
 */
@Service
@Slf4j
public class OrderServiceImpl
        extends ServiceImpl<OrdersMapper, Orders> implements OrderService {

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private DishMapper dishMapper;

    @Override
    public BaseResponse<ArrayList<OrderDTO>> page(long id, int currentPage, int pageSize) {
        ArrayList<OrderDTO> page = ordersMapper.getOrderListPage(new PageInfo(id, currentPage-1, pageSize));
        return BaseResponse.success(page);
    }

    public BaseResponse<Page<OrdersBackendDTO>> backendPage(OrderPageDTO orderPageDTO) {
        Page<OrdersBackendDTO> page = new Page<>(orderPageDTO.getCurrent(), orderPageDTO.getPageSize());
        QueryWrapper<OrdersBackendDTO> wrapper = new QueryWrapper<>();
        wrapper.eq(StringUtils.hasLength(orderPageDTO.getOrderId()), "id", orderPageDTO.getOrderId())
                .between(StringUtils.hasLength(orderPageDTO.getBeginTime()), "checkout_time", orderPageDTO.getBeginTime(), orderPageDTO.getEndTime());
        Page<OrdersBackendDTO> ordersPage = ordersMapper.ordersList(page, wrapper);
        return ordersPage.getRecords().size() > 0 ? BaseResponse.success(ordersPage) : BaseResponse.error("order is empty");
    }

    @Override
    public BaseResponse<Long> createOrder(LinkedHashMap<Long, OrderDetail> map, long canteenId) {
        // 库存检查 库存>0 , 库存-1, 创建订单 返回
        long orderId = IdUtil.getSnowflakeNextId();
        Long userId = BaseContext.get();
        BigDecimal orderAmount = new BigDecimal(0);
        for (Map.Entry<Long, OrderDetail> entry : map.entrySet()) {
            long orderDetailId = IdUtil.getSnowflakeNextId();
            long dishId = entry.getKey();
            OrderDetail orderDetail = entry.getValue();
            // 计算金额
            Dish dish = dishMapper.selectById(dishId);
            Integer count = orderDetail.getCount();
            BigDecimal amount = dish.getPrice().multiply(BigDecimal.valueOf(count));
            orderAmount = orderAmount.add(amount);
            // 组装orderDetail
            orderDetail
                    .setOrderId(orderId)
                    .setId(orderDetailId)
                    .setDishId(dishId)
                    .setAmount(amount);
            orderDetailMapper.insert(orderDetail);
        }
        Orders orders = new Orders()
                .setId(orderId)
                .setUserId(userId)
                .setCanteenId(canteenId)
                .setAmount(orderAmount)
                .setStatus(OrderStatusEnum.TO_PAY.getStatus())
                .setOrderTime(LocalDateTime.now());
        ordersMapper.insert(orders);
        return BaseResponse.success(orderId);
    }

    @Override
    public BaseResponse<BigDecimal> checkAmount(long orderId) {
        Orders orders = ordersMapper.selectById(orderId);
        return Objects.isNull(orders) ? BaseResponse.error("不存在此订单") : BaseResponse.success(orders.getAmount());
    }


    @Override
    public BaseResponse<String> payOrder(long orderId) {
        Orders orders = new Orders()
                .setId(orderId)
                .setCheckoutTime(LocalDateTime.now())
                .setStatus(OrderStatusEnum.PAID.getStatus());
        int i = ordersMapper.updateById(orders);
        return i==1?BaseResponse.success("pay success"):BaseResponse.error("pay fail");
    }
}