package my.asoul.takeout_server.exception.exceptionHandler;

import lombok.extern.slf4j.Slf4j;
import my.asoul.takeout.model.base.BaseResponse;
import my.asoul.takeout_server.exception.CommonException;
import my.asoul.takeout_server.exception.TokenException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author 4512
 * @date 2022/9/27 23:33
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private void exceptionPrint(Exception e) {
        StackTraceElement[] stackTrace = e.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTrace) {
            log.error(stackTraceElement.toString());
        }
        log.error(e.toString());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<String> exceptionHandler(Exception e) {
        exceptionPrint(e);
        return BaseResponse.error("exception!");
    }

    @ExceptionHandler(TokenException.class)
    public BaseResponse<String> tokenExceptionHandler(TokenException e) {
        log.error("exception:{}", e.getMessage());
//        exceptionPrint(e);
        return BaseResponse.error(403, e.getErrorMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseResponse<String> methodArgumentNotValidExceptionHandler(BindingResult bindingResult) {
        return BaseResponse.error(bindingResult.getAllErrors().get(0).getDefaultMessage());
    }

    @ExceptionHandler({CommonException.class})
    public BaseResponse<String> commonExceptionHandler(CommonException e) {
        exceptionPrint(e);
        return BaseResponse.error(e.getMsg());
    }
}
