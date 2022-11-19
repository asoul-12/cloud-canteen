package my.asoul.takeout.model.base;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

/**
 * @author 4512
 * @date 2022/9/26 4:38
 */
@Data
@Accessors(chain = true)
public class BaseUser implements Serializable {

    private Long id;

    @Length(min = 1, max = 20)
    private String name;

    @Length(min = 1, max = 20)
    private String account;

    @Length(min = 3, max = 30)
    private String password;


}
