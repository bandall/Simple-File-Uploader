package hello.test.web.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("login") == null) {
            log.info("비로그인 사용자 요청 URL={}", requestURI);
            log.info("/로 리다이렉트");
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
