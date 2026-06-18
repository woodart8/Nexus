package com.gentle.nexus.user.service;

import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
import com.gentle.nexus.user.domain.User;
import com.gentle.nexus.user.domain.UserRole;
import com.gentle.nexus.user.domain.UserStatus;
import com.gentle.nexus.user.dto.*;
import com.gentle.nexus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileDto getUserProfile(Long id) {
        User user = userRepository.findByIdAndUserStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        return UserProfileDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    @Transactional
    public UserRegisterResultDto register(UserRegisterDto dto) {
        try {
            User user = User.builder()
                    .ci(dto.getCi())
                    .name(dto.getName())
                    .loginId(dto.getLoginId())
                    .password(passwordEncoder.encode(dto.getPassword()))
                    .phone(dto.getPhone())
                    .email(dto.getEmail())
                    .role(UserRole.ROLE_USER)
                    .userStatus(UserStatus.ACTIVE)
                    .build();

            User savedUser = userRepository.save(user);

            return UserRegisterResultDto.from(savedUser);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException(ErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (DataAccessException e) {
            throw new BusinessException(ErrorCode.DB_ERROR);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void modifyUser(Long id, VerifiedUserProfileDto verifiedUserUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.changeCi(verifiedUserUpdateDto.getCi());
        user.changeName(verifiedUserUpdateDto.getName());
        user.changePhone(verifiedUserUpdateDto.getPhone());
        user.changeEmail(verifiedUserUpdateDto.getEmail());
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.changeUserStatus(UserStatus.DELETED);
    }

    @Transactional
    public void modifyUserPassword(Long id, UserPasswordDto userPasswordDto) {
        if (!userPasswordDto.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$")) {
            throw new BusinessException(ErrorCode.INVALID_NEW_PASSWORD);
        }
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        user.changePassword(passwordEncoder.encode(userPasswordDto.getPassword()));
    }

}
