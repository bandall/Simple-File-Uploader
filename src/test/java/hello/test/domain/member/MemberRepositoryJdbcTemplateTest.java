package hello.test.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
class MemberRepositoryJdbcTemplateTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void save() {
        Member member = new Member();
        member.setUsername("bandall");
        member.setPassword("1234");
        member.setLoginId("test1");

        Member savedMember = memberRepository.save(member);
        log.info("savedMember={}", savedMember);
    }

    @Test
    void findByLoginId() {
        String loginId = "test1";

        Optional<Member> optionalMember = memberRepository.findByLoginId(loginId);
        assertThat(optionalMember).isPresent();
        log.info("MemberFound={}", optionalMember.get());

        String loginId2 = "test2";
        Optional<Member> optionalMember2 = memberRepository.findByLoginId(loginId2);
        assertThat(optionalMember2).isNotPresent();
        log.info("MemberNull={}", optionalMember2);
    }

    @Test
    void delete() {
        Member member = new Member();
        member.setUsername("bandall77");
        member.setPassword("12345");
        member.setLoginId("test1234");

        Member savedMember = memberRepository.save(member);
        log.info("savedMember={}", savedMember);

        memberRepository.deleteById(member.getId());
        Optional<Member> optionalMember = memberRepository.findById(member.getId());
        assertThat(optionalMember).isNotPresent();
    }

    @Test
    void update() {

    }



}