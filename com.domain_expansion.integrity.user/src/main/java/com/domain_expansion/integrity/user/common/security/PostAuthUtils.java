package com.domain_expansion.integrity.user.common.security;

import com.domain_expansion.integrity.user.common.response.SuccessResponse;
import com.domain_expansion.integrity.user.presentation.response.UserResponseDto;
import org.springframework.http.ResponseEntity;

public class PostAuthUtils {

    public static boolean checkSameUser(Long principalUserId, Object returnObject) {
        if (returnObject instanceof ResponseEntity<?>) {
            Object body = ((ResponseEntity<?>) returnObject).getBody();

            if (body instanceof SuccessResponse<?> successResponse) {
                Object data = successResponse.data();

                if (data instanceof UserResponseDto userResponse) {
                    return principalUserId.equals(userResponse.userId());
                }
            }
        }
        return false;
    }

}
