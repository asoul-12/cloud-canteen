package my.asoul.takeout_server.service.order.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.asoul.takeout.model.order.OrderDetail;
import my.asoul.takeout_server.mapper.order.OrderDetailMapper;
import my.asoul.takeout_server.service.order.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author hjy
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

}