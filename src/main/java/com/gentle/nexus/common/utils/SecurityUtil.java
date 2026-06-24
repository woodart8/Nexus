package com.gentle.nexus.common.utils;

import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
import org.springframework.security.core.Authentication;

public class SecurityUtil {

    public static Long getCurrentUserId(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);
        }

        return (Long) authentication.getPrincipal();
    }

}
