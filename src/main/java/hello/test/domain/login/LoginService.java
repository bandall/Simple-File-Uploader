package hello.test.domain.login;

import hello.test.domain.member.Member;
import hello.test.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    //임시
    public Member loginCheck(String loginId, String password) {
        String hashPassword = hash256(password);

        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);
        if(memberOptional.isEmpty()) {
            return null;
        }

        Member member = memberOptional.get();

        if(member.getPassword() != passwordEncoder.encode(password)) {
            return null;
        }

        return member;
    }

    private String hash256(String raw) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return raw;
        }
        md.update(raw.getBytes());
        String hex = String.format("%064x", new BigInteger(1, md.digest()));
        return hex;
    }
}
