package my.asoul.takeout.model.order;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.LinkedHashMap;

/**
 * @author 4512
 * @date 2022/11/9 18:12
 */
@Data
@Accessors
@NoArgsConstructor
public class OrderMiniDTO {
    private Long canteenId;
    private LinkedHashMap<Long, OrderDetail> map;

    @JsonAnySetter
    public void setMap(Long key, OrderDetail value) {
        this.map.put(key, value);
    }
}
