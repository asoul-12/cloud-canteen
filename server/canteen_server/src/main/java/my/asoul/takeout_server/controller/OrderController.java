package my.asoul.takeout_server.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout.model.order.*;
import my.asoul.takeout_server.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author 4512
 * @date 2022/9/20 2:29
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    /**
     * 后台用户查询订单分页
     *
     * @param orderPageDTO dto
     * @return 分页信息
     */
    @PostMapping("/page")
    public BaseResponse<Page<OrdersBackendDTO>> backendPage(@RequestBody OrderPageDTO orderPageDTO) {
        return orderService.backendPage(orderPageDTO);
    }

    /**
     * 商家更新订单信息
     *
     * @param orders
     * @return
     */
    @PutMapping
    public BaseResponse<String> updateOrder(@RequestBody Orders orders) {
        return orderService.updateById(orders) ? BaseResponse.success("update success") : BaseResponse.error("fail");
    }

    /**
     * 小程序用户订单分页
     *
     * @param currentPage 当前页数
     * @param pageSize    一页显示内容
     * @return
     */
    @GetMapping("/{id}/{currentPage}/{pageSize}")
    public BaseResponse<ArrayList<OrderDTO>> page(@PathVariable("id") long id, @PathVariable("currentPage") int currentPage, @PathVariable("pageSize") int pageSize) {
        return orderService.page(id, currentPage, pageSize);
    }


    /**
     * 小程序创建订单
     */
    @PostMapping("/{canteenId}")
    public BaseResponse<Long> createOrder(@RequestBody LinkedHashMap<Long, OrderDetail> map, @PathVariable long canteenId) {
        return orderService.createOrder(map, canteenId);
    }

    /**
     * 小程序支付前再次核验金额
     */
    @GetMapping("/check-amount/{orderId}")
    public BaseResponse<BigDecimal> checkAmount(@PathVariable long orderId) {
        return orderService.checkAmount(orderId);
    }

    /**
     * 订单支付
     *
     * @return
     */
    @GetMapping("/pay-order/{orderId}")
    public BaseResponse<String> payOrder(@PathVariable long orderId) {
        return orderService.payOrder((orderId));
    }

}
