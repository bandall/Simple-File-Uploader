package hello.test.web.controller;

import hello.test.domain.login.LoginService;
import hello.test.domain.member.Member;
import hello.test.web.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginDto form, @RequestParam(value = "errMsg", required = false) String errMsg, Model model) {
        model.addAttribute("errMsg", errMsg);
        return "login/loginForm";
    }

//    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("loginForm") LoginDto form, BindingResult bindingResult, HttpServletRequest req, Model model) {
        log.info("로그인 시도={}", form);
        if(bindingResult.hasErrors()) {
            model.addAttribute("loginForm", form);
            return "login/loginForm";
        }

        Member loginMember = loginService.loginCheck(form.getLoginId(), form.getPassword());
        if(loginMember == null) {
            log.info("로그인 실패");
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            model.addAttribute("loginForm", form);
            return "login/loginForm";
        }

        log.info("로그인 성공 UserId={}, UserName={}", loginMember.getId(), loginMember.getUsername());

        HttpSession session = req.getSession();
        session.setAttribute("login", "true");
        session.setAttribute("username", loginMember.getUsername());
        session.setAttribute("id", loginMember.getId());

        return "redirect:/";
    }

//    @PostMapping("/logout")
    public String logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        log.info("유저 로그아웃 UserId={}, UserName={}", session.getAttribute("id"), session.getAttribute("username"));
        if(session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
