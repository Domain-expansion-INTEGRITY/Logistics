package com.domain_expansion.integrity.user.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.domain_expansion.integrity.user.common.exception.UserException;
import com.domain_expansion.integrity.user.common.message.ExceptionMessage;
import com.domain_expansion.integrity.user.common.security.UserDetailsImpl;
import com.domain_expansion.integrity.user.domain.model.UserRole;
import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;
import com.domain_expansion.integrity.user.presentation.request.UserSearchCondition;
import com.domain_expansion.integrity.user.presentation.request.UserUpdateRequestDto;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

// do return when
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserServiceImplTest {


    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 유저 생성
    private UserCreateRequestDto generateMockUser(String username, String phone, UserRole role,
        String slackId) {
        return new UserCreateRequestDto(
            username,
            "password",
            role,
            phone,
            slackId
        );
    }

    // 기본 페이징 생성
    private PageRequest getPageRequestInfo() {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "createdAt");
        return PageRequest.of(0, 10, sort);
    }

    // Security 정보 생성
    private void registerSecurity() {
        // 생성
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(
            UserRole.MASTER.getAuthority());
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
            new UserDetailsImpl(1L, authorities, UserRole.MASTER.getAuthority()), null,
            authorities);

        // 등록
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    @Nested
    @DisplayName("유저 생성 테스트")
    class createUser {

        @BeforeEach
        void initBefore() {
            // User과 연관된 테이블은 현재 미존재
            jdbcTemplate.execute("TRUNCATE TABLE p_user");
        }


        @Test
        @DisplayName("유저 생성 - 성공")
        void 유저_생성_및_조회_테스트() {
            // given
            UserCreateRequestDto userCreateRequestDto = generateMockUser("username",
                "010-1111-1111", UserRole.MASTER, "slk");

            // when
            Long userId = userService.createUser(userCreateRequestDto);
            UserResponseDto userDto = userService.findUserById(userId);

            // then
            assertThat(userId).isInstanceOf(Long.class);
            assertThat(userDto.userId()).isEqualTo(userId);
            assertThat(userDto.role()).isEqualTo(UserRole.MASTER);
            assertThat(userDto.phoneNumber()).isEqualTo(userCreateRequestDto.phoneNumber());
            assertThat(userDto.slackId()).isEqualTo(userCreateRequestDto.slackId());

        }

        @Test
        @DisplayName("유저 생성 실패 - 잘못된 휴대전화")
        void 유저_휴대폰번호_잘못_기입() {
            // given
            UserCreateRequestDto userCreateRequestDto = generateMockUser("username",
                "010-200-200", UserRole.MASTER, "slk");

            // when
            // then
            assertThatThrownBy(() -> {
                userService.createUser(userCreateRequestDto);
            }).isInstanceOf(UserException.class);
        }

        @Test
        @DisplayName("유저 생성 실패 - 중복 휴대전화")
        void 유저_중복번호_추가() {
            // given
            UserCreateRequestDto userOne = generateMockUser("one_dup", "010-1111-1112",
                UserRole.MASTER, "slack");
            UserCreateRequestDto userTwo = generateMockUser("two_dup", "010-1111-1112",
                UserRole.MASTER, "slacktwo");

            userService.createUser(userOne);

            // when
            // then
            assertThatThrownBy(() -> {
                userService.createUser(userTwo);
            }).isInstanceOf(DataIntegrityViolationException.class);
        }

        @Test
        @DisplayName("유저 생성 실패 - 중복 슬랙 아이디")
        void 유저_중복슬랙_추가() {
            // given
            UserCreateRequestDto userOne = generateMockUser("one_slack_dup", "010-1111-1112",
                UserRole.MASTER, "slackdup");
            UserCreateRequestDto userTwo = generateMockUser("two_slack_dup", "010-1111-1113",
                UserRole.MASTER, "slackdup");

            userService.createUser(userOne);

            // when
            // then
            assertThatThrownBy(() -> {
                userService.createUser(userTwo);
            }).isInstanceOf(DataIntegrityViolationException.class);
        }


    }

    @Nested
    @DisplayName("유저 조회 테스트")
    class findUser {

        @BeforeEach
        void initBefore() {
            // User과 연관된 테이블은 현재 미존재
            jdbcTemplate.execute("TRUNCATE TABLE p_user");
        }

        @Test
        @DisplayName("유저 목록 조회")
        void 유저_목록_조회() {
            // given
            UserCreateRequestDto userOne = generateMockUser("one_slack_dup", "010-1111-1112",
                UserRole.MASTER, "slackOne");
            UserCreateRequestDto userTwo = generateMockUser("two_slack_dup", "010-1111-1113",
                UserRole.MASTER, "slackTwo");

            userService.createUser(userOne);
            userService.createUser(userTwo);

            // when
            List<UserResponseDto> list = userService.findUserList(getPageRequestInfo(),
                    new UserSearchCondition(null)).map((userResponseDto -> userResponseDto))
                .toList();

            // then
            assertThat(list.size()).isEqualTo(2);

        }

        @Test
        @DisplayName("유저 역할 구분 조회")
        void 유저_역할별_조회() {
            // given
            UserCreateRequestDto userOne = generateMockUser("one_slack_dup", "010-1111-1112",
                UserRole.MASTER, "slackOne");
            UserCreateRequestDto userTwo = generateMockUser("two_slack_dup", "010-1111-1113",
                UserRole.MASTER, "slackTwo");
            UserCreateRequestDto userThree = generateMockUser("three_slack_dup", "010-1111-1114",
                UserRole.HUB_COMPANY, "slackThree");

            userService.createUser(userOne);
            userService.createUser(userTwo);
            userService.createUser(userThree);

            // when
            List<UserResponseDto> list = userService.findUserList(getPageRequestInfo(),
                    new UserSearchCondition(UserRole.MASTER)).map((userResponseDto -> userResponseDto))
                .toList();

            // then
            assertThat(list.size()).isEqualTo(2);
        }

    }

    @Nested
    @DisplayName("유저 업데이트 테스트")
    class updateUser {

        @BeforeEach
        void initBefore() {
            // User과 연관된 테이블은 현재 미존재
            jdbcTemplate.execute("TRUNCATE TABLE p_user");
        }

        @Test
        @DisplayName("유저 성공 업데이트")
        void 유저_기본_업데이트() {
            // given
            UserCreateRequestDto userOne = generateMockUser("one_slack_dup", "010-1111-1112",
                UserRole.MASTER, "slackOne");

            Long userId = userService.createUser(userOne);
            UserUpdateRequestDto updateInfo = new UserUpdateRequestDto(UserRole.HUB_COMPANY,
                "newSlack", "010-1234-1122");

            // when
            UserResponseDto responseDto = userService.updateUserById(userId, updateInfo);

            // then
            assertThat(responseDto.role()).isEqualTo(updateInfo.role());
            assertThat(responseDto.slackId()).isEqualTo(updateInfo.slackId());
            assertThat(responseDto.phoneNumber()).isEqualTo(updateInfo.phoneNumber());
        }

        @Test
        @DisplayName("유저 실패 - 이미 있는 정보")
        void 유저_업데이트_실패() {
            // given
            UserCreateRequestDto userOne = generateMockUser("one_slack_dup", "010-1111-1112",
                UserRole.MASTER, "slackOne");

            UserCreateRequestDto userTwo = generateMockUser("two_slack_dup", "010-1111-1113",
                UserRole.MASTER, "slackTwo");

            userService.createUser(userOne);
            Long userId = userService.createUser(userTwo);
            UserUpdateRequestDto updateInfo = new UserUpdateRequestDto(UserRole.HUB_COMPANY,
                userOne.slackId(), "010-1234-1122");

            // when
            // then
            assertThatThrownBy(() -> {
                userService.updateUserById(userId, updateInfo);
            }).isInstanceOf(UserException.class);

        }


    }

    @Nested
    @DisplayName("유저 삭제 테스트")
    class deleteUser {

        @BeforeEach
        void initBefore() {
            // User과 연관된 테이블은 현재 미존재
            jdbcTemplate.execute("TRUNCATE TABLE p_user");
        }

        @Test
        @DisplayName("유저 삭제")
        void 유저_삭제_조회() {
            // given
            UserCreateRequestDto userOne = generateMockUser("one_slack_dup", "010-1111-1112",
                UserRole.MASTER, "slackOne");

            Long userId = userService.createUser(userOne);
            registerSecurity();

            // when
            userService.deleteUserById(userId);

            // then
            assertThatThrownBy(() -> {
                userService.findUserById(userId);
            }).isInstanceOf(UserException.class)
                .hasMessageContaining(ExceptionMessage.USER_NOT_FOUND.getMessage());

        }

        @Test
        @DisplayName("유저 삭제 후 목록")
        void 유저_삭제_목록조회() {
            // given
            UserCreateRequestDto userOne = generateMockUser("one_slack_dup", "010-1111-1112",
                UserRole.MASTER, "slackOne");

            UserCreateRequestDto userTwo = generateMockUser("two_slack_dup", "010-1111-1113",
                UserRole.MASTER, "slackTwo");

            Long userId = userService.createUser(userOne);
            userService.createUser(userTwo);
            registerSecurity();

            // when
            userService.deleteUserById(userId);

            // then
            List<UserResponseDto> list = userService.findUserList(getPageRequestInfo(),
                new UserSearchCondition(UserRole.MASTER)).map((dto) -> dto).toList();
            assertThat(list.size()).isEqualTo(1);
        }

    }

}