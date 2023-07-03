package hello.test.web.controller;


import hello.test.domain.member.MemberDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@SessionAttribute(name="login", required = false) Boolean loginStatus,
                       @SessionAttribute(name="username", required = false) String username,
                       @AuthenticationPrincipal MemberDetails memberDetails,
                       Model model) {
        if(loginStatus == null) {
            return "home/home";
        }
        model.addAttribute("isAdmin", memberDetails.getAuthorities().toString().equals("[ROLE_ADMIN]"));
        model.addAttribute("username", username);
        return "home/mainPage";
    }

    @GetMapping("/ex")
    public String error() {
        throw new RuntimeException("사용자 테스트 오류");
    }

    @GetMapping("/whoami")
    public String whoami(@AuthenticationPrincipal MemberDetails memberDetails) {
        log.info("{}", memberDetails);
        log.info("{}", memberDetails.getAuthorities().toString().equals("[USER]"));
        return "home/mainPage";
    }
}
