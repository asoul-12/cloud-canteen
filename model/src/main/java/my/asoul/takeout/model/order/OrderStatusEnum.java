package my.asoul.takeout.model.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 4512
 * @date 2022/11/4 1:26
 */
@Getter
@AllArgsConstructor
public enum OrderStatusEnum {
    /* */
    TO_PAY(0, "待支付"),

    EXPIRED(-1, "已过期"),

    PAID(1, "已支付");

    private final int status;

    private final String description;
}
