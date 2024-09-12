package com.domain_expansion.integrity.user.domain.model.converter;

import com.domain_expansion.integrity.user.common.exception.UserException;
import com.domain_expansion.integrity.user.common.message.ExceptionMessage;
import com.domain_expansion.integrity.user.domain.model.UserRole;
import org.springframework.core.convert.converter.Converter;


public class UserRoleConverter implements Converter<String, UserRole> {

    @Override
    public UserRole convert(String source) {
        try {
            return UserRole.valueOf(source);
        } catch (Exception e) {
            throw new UserException(ExceptionMessage.INVALID_PASSWORD);
        }


    }

}
