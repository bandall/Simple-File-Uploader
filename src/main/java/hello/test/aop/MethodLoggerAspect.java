package hello.test.aop;

import hello.test.aop.trace.MethodLogger;
import hello.test.aop.trace.TraceStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class MethodLoggerAspect {
    private final MethodLogger methodLogger;

    public MethodLoggerAspect(MethodLogger methodLogger) {
        this.methodLogger = methodLogger;
    }

    @Around("execution(* hello.test.domain.upload..*(..)) || execution(* hello.test.web.controller..*(..))")
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joinPoint.getSignature().toShortString();
            status = methodLogger.begin(message);

            //target 호출
            Object result = joinPoint.proceed();
            methodLogger.end(status);
            return result;
        } catch (Exception e) {
            methodLogger.exception(status, e);
            throw e;
        }
    }
}
