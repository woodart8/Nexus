package com.gentle.nexus.user.repository;

import com.gentle.nexus.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
