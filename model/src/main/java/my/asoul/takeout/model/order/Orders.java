package my.asoul.takeout.model.order;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单
 *
 * @author hjy
 */
@Data
@Accessors(chain = true)
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private Long canteenId;

    private Integer status;

    private LocalDateTime orderTime;

    private LocalDateTime checkoutTime;

    private BigDecimal amount;

    private String remark;

}
