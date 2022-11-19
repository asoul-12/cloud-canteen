package my.asoul.takeout.model.dish;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 菜品
 *
 * @author hjy
 */
@Data
@Accessors(chain = true)
public class Dish implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @Length(min = 1)
    private String name;

    @NotNull
    private Long categoryId;

    private Long canteenId;

    @TableField(exist = false)
    private String category;

    @Min(0)
    private BigDecimal price;

    private String image;

    private String description;

    private Boolean status;

    private Integer sort;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

}
