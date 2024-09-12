package com.domain_expansion.integrity.gateway.domain.redis;

import com.domain_expansion.integrity.gateway.domain.UserRole;
import com.domain_expansion.integrity.gateway.domain.dto.UserAuthDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RedisDomainServiceTest {

    @Autowired
    private RedisDomainService redisDomainService;

    @Test
    public void 레디스_저장_읽기_테스트() {
        // given
        String username = "yahoo";
        UserRole role = UserRole.MASTER;
        Long userId = 1L;

        UserAuthDto userAuthDto = new UserAuthDto(userId, "username",
            UserRole.MASTER.getAuthority());

        // when
        redisDomainService.setUserData(userAuthDto);
        UserAuthDto afterUserAuth = redisDomainService.findUserAuth(userId);

        // then
        Assertions.assertThat(afterUserAuth.username()).isEqualTo(userAuthDto.username());
        Assertions.assertThat(afterUserAuth.role()).isEqualTo(UserRole.MASTER.getAuthority());
    }

}