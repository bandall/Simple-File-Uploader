package hello.test;

import hello.test.web.filter.LogFilter;
import hello.test.web.interceptor.LogInterceptor;
import hello.test.web.interceptor.LoginCheckInterceptor;
import jakarta.servlet.Filter;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean logFilter() {
        FilterRegistrationBean<Filter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new LogFilter());
        filterRegistrationBean.setOrder(SecurityProperties.DEFAULT_FILTER_ORDER - 1);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LogFilter())
//                .order(1)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/css/**", "/*.ico", "/error");
//
//        registry.addInterceptor(new LoginCheckInterceptor())
//                .order(2)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/", "/login", "/css/**", "/*.ico", "/error");
//
//    }
}
