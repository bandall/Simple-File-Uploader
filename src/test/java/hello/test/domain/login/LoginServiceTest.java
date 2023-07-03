package hello.test.domain.login;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
class LoginServiceTest {

    @Test
    void adminPasswordHash() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        log.info("hashPassword={}", bCryptPasswordEncoder.encode(""));
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