package my.asoul.takeout.model.staff;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author hjy
 */
@Data
public class Employee {

    private Long id;

    private String name;

    @Length(min = 5, max = 20 ,message = "手机号码有误")
    private String phone;

    @NotNull(message = "性别不能为空")
    @NotBlank(message = "性别不能为空")
    private String sex;

    @Length(min = 12, max = 20,message = "用户身份证有误")
    private String idNumber;

    private Long canteenId;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    public Long getCanteenId() {
        return canteenId;
    }

    public void setCanteenId(Long canteenId) {
        this.canteenId = canteenId;
    }
}
