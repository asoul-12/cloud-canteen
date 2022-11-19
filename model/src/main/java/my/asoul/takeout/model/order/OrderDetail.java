package my.asoul.takeout.model.order;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细
 * @author hjy
 */
@Data
@Accessors(chain = true)
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String image;

    private Long orderId;

    private Long dishId;

    private Integer count;

    private BigDecimal amount;

}
