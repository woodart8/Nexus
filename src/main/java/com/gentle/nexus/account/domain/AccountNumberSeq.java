package com.gentle.nexus.account.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "account_number_seq")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class AccountNumberSeq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

}
