package com.domain_expansion.integrity.auth.common.jwt;

import com.domain_expansion.integrity.auth.domain.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;
import java.lang.reflect.Field;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class JwtUtilsTest {


    private final String TEST_JWT_KEY = "7Leo7JeF7Leo7JeF7Leo7JeF7Leo7JeF7Leo7JeF7Leo7JeF7Leo7JeF";

    @Test
    public void JWT_생성_검증() throws NoSuchFieldException, IllegalAccessException {
        // given
        JwtUtils jwtUtils = new JwtUtils();
        Field secretKeyField = JwtUtils.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true);
        secretKeyField.set(jwtUtils, TEST_JWT_KEY);

        Field keyField = JwtUtils.class.getDeclaredField("key");
        keyField.setAccessible(true);
        keyField.set(jwtUtils, Keys.hmacShaKeyFor(TEST_JWT_KEY.getBytes()));
        Long userId = 1L;

        String token = jwtUtils.createToken(userId, UserRole.MASTER);

        // when
        boolean validToken = jwtUtils.checkValidJwtToken(token.substring(7));
        boolean InvalidToken = jwtUtils.checkValidJwtToken(token);

        // then
        Assertions.assertThat(validToken).isTrue();
        Assertions.assertThat(InvalidToken).isFalse();
    }

    @Test
    public void JWT_생성_조회() throws NoSuchFieldException, IllegalAccessException {
        // given
        JwtUtils jwtUtils = new JwtUtils();
        Field secretKeyField = JwtUtils.class.getDeclaredField("secretKey");
        secretKeyField.setAccessible(true);
        secretKeyField.set(jwtUtils, TEST_JWT_KEY);

        Field keyField = JwtUtils.class.getDeclaredField("key");
        keyField.setAccessible(true);
        keyField.set(jwtUtils, Keys.hmacShaKeyFor(TEST_JWT_KEY.getBytes()));
        Long userId = 1L;

        String token = jwtUtils.createToken(userId, UserRole.MASTER);

        // when
        Claims bodyFromJwt = jwtUtils.getBodyFromJwt(token.substring(7));

        // then
        Assertions.assertThat(String.valueOf(userId)).isEqualTo(bodyFromJwt.getSubject());

    }


}