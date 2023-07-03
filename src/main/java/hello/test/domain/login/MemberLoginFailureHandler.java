package hello.test.domain.login;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j
public class MemberLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String loginId = request.getParameter("loginId");
        String errMsg = getExceptionMessage(exception);
        String redirectUrl = "/login?loginId=" + loginId + "&errMsg=" + URLEncoder.encode(errMsg, StandardCharsets.UTF_8);
        setDefaultFailureUrl(redirectUrl);
        super.onAuthenticationFailure(request, response, exception);
    }

    private String getExceptionMessage(AuthenticationException exception) {
        if(exception instanceof BadCredentialsException) {
            return "인증에 실패했습니다.";
        } else if (exception instanceof UsernameNotFoundException) {
            return "계정이 존재하지 않습니다.";
        } else if (exception instanceof AccountExpiredException) {
            return "계정이 만료되었습니다.";
        } else if (exception instanceof CredentialsExpiredException) {
            return "비밀번호가 만료되었습니다.";
        } else if (exception instanceof DisabledException) {
            return "계정이 비활성화 되었습니다.";
        } else if (exception instanceof LockedException) {
            return "계정이 잠김 상태 입니다.";
        } else {
            return "예상치 못한 오류가 발생하였습니다.";
        }
    }
}
