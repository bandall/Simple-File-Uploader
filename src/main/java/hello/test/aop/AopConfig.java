package hello.test.aop;

import hello.test.aop.trace.MethodLogger;
import hello.test.aop.trace.TraceId;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AopConfig {
    @Bean
    public MethodLoggerAspect methodLoggerAspect() {
        return new MethodLoggerAspect(methodLogger());
    }

    public MethodLogger methodLogger() {
        return new MethodLogger();
    }
}
