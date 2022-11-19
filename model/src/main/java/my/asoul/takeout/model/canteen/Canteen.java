package my.asoul.takeout.model.canteen;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 4512
 * @date 2022/11/1 22:50
 */
@Data
@Accessors(chain = true)
public class Canteen implements Serializable {

    private Long id;
    private String name;
    private String avatar;
    private Long hostId;
}
