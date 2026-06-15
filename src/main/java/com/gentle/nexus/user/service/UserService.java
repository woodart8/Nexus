package com.gentle.nexus.user.service;

import com.gentle.nexus.user.domain.User;
import com.gentle.nexus.user.domain.UserStatus;
import com.gentle.nexus.user.dto.UserRegisterDto;
import com.gentle.nexus.user.dto.UserRegisterResultDto;
import com.gentle.nexus.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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

}
