package com.gentle.nexus.user.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false)
    String ci;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String password;

    @Column(nullable = false)
    String phone;

    @Column(nullable = false)
    String email;

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

    public void changeUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

}