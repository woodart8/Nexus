package com.gentle.nexus.user.facade;


import com.gentle.nexus.auth.service.AuthService;
import com.gentle.nexus.common.infra.pass.dto.PassResponseDto;
import com.gentle.nexus.user.dto.UserProfileDto;
import com.gentle.nexus.user.dto.VerifiedUserProfileDto;
import com.gentle.nexus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final AuthService authService;
    private final UserService userService;

    public void updateUserProfile(Long id, UserProfileDto userProfileDto) {
        PassResponseDto passResponseDto = authService.issueCi(userProfileDto.getName(), userProfileDto.getPhone());
        VerifiedUserProfileDto verifiedUserProfileDto = VerifiedUserProfileDto.from(passResponseDto.getCi(), userProfileDto);
        userService.modifyUser(id, verifiedUserProfileDto);
    }

}
