package com.gentle.nexus.user.repository;

import com.gentle.nexus.user.domain.User;
import com.gentle.nexus.user.domain.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndUserStatus(Long id, UserStatus userStatus);
}
