package hello.test.domain.login;

import hello.test.domain.member.MemberDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.io.IOException;

@Slf4j
public class MemberLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) authentication.getDetails();
        log.info("IP={}, SessionId={}", webAuthenticationDetails.getRemoteAddress(), webAuthenticationDetails.getSessionId());
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        memberDetails.setPassword("ERASED");
        log.info("로그인 성공 UserId={}, UserName={}", memberDetails.getId(), memberDetails.getUsername());

        HttpSession session = request.getSession();
        session.setAttribute("login", "true");
        session.setAttribute("username", memberDetails.getUsername());
        session.setAttribute("id", memberDetails.getId());
        session.setAttribute("sessionId", webAuthenticationDetails.getSessionId());
        response.sendRedirect("/");
    }
}
