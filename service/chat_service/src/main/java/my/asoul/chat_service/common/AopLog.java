package my.asoul.chat_service.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author 4512
 * @date 2022/10/8 14:30
 */
@Slf4j
@Component("aopLog2")
@Scope("prototype")
@Aspect
public class AopLog {

    @Pointcut("execution(* my.asoul.chat_service.controller.*.*(..))")
    public void logPointCut() {
    }

    @Around("logPointCut()")
    public Object process(ProceedingJoinPoint point) throws Throwable {
        MethodSignature signature = (MethodSignature) point.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = point.getArgs();

        log.info("method-name:{}", signature.getName());
        for (int i = 0; i < parameterNames.length; i++) {
            if (args[i] != null) {
                log.info("method[{}]-args:{}---{}", signature.getName(), parameterNames[i], args[i]);
            } else {
                log.info("method[{}]-args:{}---null", signature.getName(), parameterNames[i]);
            }
        }
        Object proceed = point.proceed();
        log.info("method-result:{}", proceed.toString());
        return proceed;
    }


}
