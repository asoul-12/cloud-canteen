package my.asoul.takeout.model.order;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

/**
 * @author 4512
 * @date 2022/9/22 22:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private Long id;
    private String canteenName;
    private Orders orders;
}

