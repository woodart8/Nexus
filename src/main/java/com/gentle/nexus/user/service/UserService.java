package com.gentle.nexus.user.service;

import com.gentle.nexus.user.domain.User;
import com.gentle.nexus.user.domain.UserStatus;
import com.gentle.nexus.user.dto.*;
import com.gentle.nexus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserProfileDto getUserProfile(Long id) {
        User user = userRepository.findByIdAndUserStatus(id, UserStatus.ACTIVE).orElseThrow();

        return UserProfileDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }

    @Transactional
    public UserRegisterResultDto register(UserRegisterDto userRegisterDto) {
        User user = User.builder()
                .ci(userRegisterDto.getCi())
                .name(userRegisterDto.getName())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .phone(userRegisterDto.getPhone())
                .email(userRegisterDto.getEmail())
                .userStatus(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);

        return UserRegisterResultDto.from(savedUser);
    }

    @Transactional
    public void modifyUser(Long id, VerifiedUserProfileDto verifiedUserUpdateDto) {
        User user = userRepository.findById(id).orElseThrow();
        user.changeCi(verifiedUserUpdateDto.getCi());
        user.changeName(verifiedUserUpdateDto.getName());
        user.changePhone(verifiedUserUpdateDto.getPhone());
        user.changeEmail(verifiedUserUpdateDto.getEmail());
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.changeUserStatus(UserStatus.DELETED);
    }

    @Transactional
    public void modifyUserPassword(Long id, UserPasswordDto userPasswordDto) {
        if (!userPasswordDto.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$")) {
            throw new IllegalArgumentException("적합하지 않은 비밀번호 입니다.");
        }
        User user = userRepository.findById(id).orElseThrow();
        user.changePassword(passwordEncoder.encode(userPasswordDto.getPassword()));
    }

}
