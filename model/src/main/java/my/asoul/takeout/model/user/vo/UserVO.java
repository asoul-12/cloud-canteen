package my.asoul.takeout.model.user.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author 4512
 * @date 2022/10/4 12:35
 */
@Data
@Accessors(chain = true)
public class UserVO {
    private Long id;
    private String name;
    private Long canteenId;
    private String canteenName;
    private String account;
    private String avatar;

}
