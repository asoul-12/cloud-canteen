package my.asoul.takeout.model.user;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import my.asoul.takeout.model.base.BaseUser;

/**
 * 用户信息
 *
 * @author hjy
 */
@Data
@Accessors(chain = true)
@ToString(callSuper = true)
public class User extends BaseUser {
    private String avatar;
    private Long canteenId;
}
