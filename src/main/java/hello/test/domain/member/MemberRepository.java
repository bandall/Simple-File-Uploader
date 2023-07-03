package hello.test.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    void deleteById(Long id);

//    Member update(Long id, Member updateMemberInfo);

    Optional<Member> findByLoginId(String loginId);

    Optional<Member> findById(Long id);

    int countMember();
}
