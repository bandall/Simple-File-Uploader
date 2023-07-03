package hello.test.domain.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
public class MemberRepositoryJdbcTemplate implements MemberRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MemberRepositoryJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(login_id, password, username) values(:loginId,:password,:username)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(member);
        jdbcTemplate.update(sql, params, keyHolder);

        long key = keyHolder.getKey().longValue();
        member.setId(key);
        return member;
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from member where id=:id";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);
        jdbcTemplate.update(sql, params);
    }

//    @Override
//    public Member update(Long id, Member updateMemberInfo) {
//        return null;
//    }

    @Override
    public Optional<Member> findByLoginId(String loginId) {
        String sql = "select * from member where login_id=:loginId";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("loginId", loginId);

        try {
            Member member = jdbcTemplate.queryForObject(sql, params, memberRowMapper());
            return Optional.of(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id=:id";
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id", id);

        try {
            Member member = jdbcTemplate.queryForObject(sql, params, memberRowMapper());
            return Optional.of(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public int countMember() {
        String sql = "select count(*) from member";
        int count;
        try {
            count = jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(), Integer.class);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
        return count;
    }

    private RowMapper<Member> memberRowMapper() {
//        return BeanPropertyRowMapper.newInstance(Member.class);
        return ((rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setUsername(rs.getString("username"));
            member.setLoginId(rs.getString("login_id"));
            member.setPassword(rs.getString("password"));
            member.setRole(rs.getString("role"));
            return member;
        });
    }

}
