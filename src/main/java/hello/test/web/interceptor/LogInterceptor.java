package hello.test.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    public static final String TRACE_ID = "traceId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        MDC.put(TRACE_ID, uuid);

        HttpSession session = request.getSession();
        if(session.getAttribute("login") != null) {
            String userId = session.getAttribute("id").toString();
            log.info("[REQUEST URI : {}, METHOD : {}] [UserId={}]", requestURI, request.getMethod(), userId);
        } else {
            log.info("[REQUEST URI : {}, METHOD : {}] [login=false]", requestURI, request.getMethod());
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if(ex != null) {
            log.info("Exception 발생", ex);
        }
        MDC.clear();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
