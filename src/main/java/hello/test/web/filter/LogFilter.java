package hello.test.web.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
public class LogFilter implements Filter {
    public static final String TRACE_ID = "traceId";
    public static final String[] noFilterUrl = {"/css/**", "/*.ico", "/error"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        HttpSession session = httpRequest.getSession(true);

        if(PatternMatchUtils.simpleMatch(noFilterUrl, requestURI)) {
            chain.doFilter(request, response);
            MDC.clear();
            return;
        }

        MDC.put(TRACE_ID, uuid);
        long startTime = System.currentTimeMillis();
        if(session.getAttribute("login") != null) {
            Object userId = session.getAttribute("id");
            if(userId == null) {
                log.info("[REQUEST URI : {}, METHOD : {}] [UserId={}]", requestURI, httpRequest.getMethod(), null);
            } else {
                log.info("[REQUEST URI : {}, METHOD : {}] [UserId={}, sessionId={}]", requestURI, httpRequest.getMethod(), userId.toString(), session.getAttribute("sessionId"));
            }

        } else {
            log.info("[REQUEST URI : {}, METHOD : {}] [login=false]", requestURI, httpRequest.getMethod());
        }

        chain.doFilter(request, response);
        long totalTime = System.currentTimeMillis() - startTime;
        log.info("Response Time = {}ms", totalTime);
        MDC.clear();
    }
}
