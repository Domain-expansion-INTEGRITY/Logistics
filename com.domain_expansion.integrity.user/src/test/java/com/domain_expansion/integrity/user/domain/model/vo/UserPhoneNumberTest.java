package com.domain_expansion.integrity.user.domain.model.vo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;


class UserPhoneNumberTest {


    @Test
    public void 맞는_REGEX_테스트() {
        // given
        String phoneOne = "010-1234-5678";
        String phoneTwo = "010-234-2020";

        // when
        boolean isValidOne = Pattern.matches(UserPhoneNumber.PHONE_NUMBER_PATTERN, phoneOne);
        boolean isValidTwo = Pattern.matches(UserPhoneNumber.PHONE_NUMBER_PATTERN, phoneTwo);

        // then
        assertThat(isValidOne).isTrue();
        assertThat(isValidTwo).isTrue();
    }

    @Test
    public void 틀린_REGEX_테스트() {
        // given
        String phoneOne = "0120-1234-5678";
        String phoneTwo = "0102342020";
        String phoneThree = "010-29291231";

        // when
        boolean isValidOne = Pattern.matches(UserPhoneNumber.PHONE_NUMBER_PATTERN, phoneOne);
        boolean isValidTwo = Pattern.matches(UserPhoneNumber.PHONE_NUMBER_PATTERN, phoneTwo);
        boolean isValidThree = Pattern.matches(UserPhoneNumber.PHONE_NUMBER_PATTERN, phoneThree);

        // then
        assertThat(isValidOne).isFalse();
        assertThat(isValidTwo).isFalse();
        assertThat(isValidThree).isFalse();
    }

}