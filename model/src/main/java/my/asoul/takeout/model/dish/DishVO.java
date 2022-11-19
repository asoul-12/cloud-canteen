package my.asoul.takeout.model.dish;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 4512
 * @date 2022/9/16 23:09
 */
@Data
@Accessors(chain = true)
public class DishVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer status;


}
