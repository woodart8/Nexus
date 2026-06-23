package com.gentle.nexus.user.domain;

import com.gentle.nexus.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String ci;

    @Column(nullable = false)
    String name;

    @Column(unique = true, nullable = false)
    String loginId;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String phone;

    @Column(nullable = false)
    String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserStatus userStatus;

    public void changeCi(String ci) {
        this.ci = ci;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changePhone(String phone) {
        this.phone = phone;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    public void changeRole(UserRole role) {
        this.role = role;
    }

    public void changeUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

}