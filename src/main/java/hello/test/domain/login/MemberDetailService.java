package hello.test.domain.login;

import hello.test.domain.member.Member;
import hello.test.domain.member.MemberDetails;
import hello.test.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByLoginId(loginId);

        if(memberOptional.isEmpty()) {
            throw new UsernameNotFoundException("잘못된 아이디 입니다.");
        }
        Member member = memberOptional.get();
        MemberDetails memberDetails = new MemberDetails();
        memberDetails.setUsername(member.getUsername());
        memberDetails.setId(member.getId());
        memberDetails.setPassword(member.getPassword());
        memberDetails.setEnabled(true);
        memberDetails.setAccountNonExpired(true);
        memberDetails.setCredentialsNonExpired(true);
        memberDetails.setAccountNonLocked(true);
        memberDetails.setAuthorities(Collections.singleton(new SimpleGrantedAuthority("ROLE_"+member.getRole())));

//        UserDetails userDetails = User.builder()
//                .username(member.getUsername())
//                .password(member.getPassword())
//                .roles(member.getRole())
//                .build();
//        log.info("userDetails={}", userDetails);
        log.info("UserDetailsService: {}", memberDetails);
        return memberDetails;
    }

}
