package my.asoul.takeout.model.order;

import lombok.Data;

/**
 * 后台订单dto
 * @author 4512
 * @date 2022/9/28 3:10
 */
@Data
public class OrdersBackendDTO extends Orders {

    private String userName;

    private String consignee;

    private String phone;

    private String address;

}
