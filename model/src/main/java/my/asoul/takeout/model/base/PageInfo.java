package my.asoul.takeout.model.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author 4512
 * @date 2022/11/9 13:27
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageInfo {
    private Long userId;
    private Integer currentPage;
    private Integer pageSize;
}
