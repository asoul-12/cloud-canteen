package my.asoul.takeout_server.exception.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout_server.controller.CategoryController;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 4512
 * @date 2022/9/14 20:07
 */
@Slf4j
@RestControllerAdvice( basePackageClasses = CategoryController.class)
public class CategoryExceptionHandler {

    @ExceptionHandler(DuplicateKeyException.class)
    public BaseResponse<String> exceptionHandler(Exception e) {
        return BaseResponse.error("该分类已存在");
    }
}
