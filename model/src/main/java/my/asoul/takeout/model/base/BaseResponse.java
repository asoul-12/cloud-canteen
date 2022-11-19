package my.asoul.takeout.model.base;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @author hjy
 */
@Data
public class BaseResponse<T> implements Serializable {
    @Override
    public String toString() {
        if (Objects.isNull(data)) {
            return "BaseResponse{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", map=" + map +
                    '}';
        } else {
            return "BaseResponse{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" +
                    data.toString()
                    +
                    ", map=" + map +
                    '}';
        }
    }

    /**
     * 编码：1成功，0和其它数字为失败
     */
    private Integer code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 数据
     */
    private T data;

    /**
     * 动态数据
     */
    private Map map = new HashMap<Object, Object>();

    public static <T> BaseResponse<T> success(T object) {
        BaseResponse<T> baseResponse = new BaseResponse<T>();
        baseResponse.data = object;
        baseResponse.code = 1;
        return baseResponse;
    }

    public static <T> BaseResponse<T> success(String msg, T object) {
        BaseResponse<T> baseResponse = new BaseResponse<T>();
        baseResponse.msg = msg;
        baseResponse.data = object;
        baseResponse.code = 1;
        return baseResponse;
    }


    public static <T> BaseResponse<T> error(String msg) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.msg = msg;
        baseResponse.code = 0;
        return baseResponse;
    }

    public static <T> BaseResponse<T> error(Integer code, String msg) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.msg = msg;
        baseResponse.code = code;
        return baseResponse;
    }


    public BaseResponse<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }

}
