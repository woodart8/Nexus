package com.gentle.nexus.user.domain;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(unique = true, nullable = false)
    String ci;

    @Column(nullable = false)
    String username;

    @Column
    String password;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    UserStatus userStatus;

}