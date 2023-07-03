package hello.test.web.controller;


import hello.test.domain.member.MemberRepository;
import hello.test.domain.upload.FileMetaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;
    private final FileMetaRepository fileMetaRepository;

    @GetMapping("/admin")
    public String getAdminMainPage(@SessionAttribute(name = "username", required = false) String username, Model model) {
        model.addAttribute("username", username);
        return "home/adminHome";
    }


    @ResponseBody
    @GetMapping("/admin/info")
    public Map<String, Object> getServiceInfo() {
        int memberCount = memberRepository.countMember();
        int fileCount = fileMetaRepository.countFile();
        long totalFileSize = fileMetaRepository.countTotalFileSize();

        Map<String, Object> map = new HashMap<>();
        map.put("memberCount", memberCount);
        map.put("fileCount", fileCount);
        map.put("totalFileSize", totalFileSize);
        return map;
    }


}
