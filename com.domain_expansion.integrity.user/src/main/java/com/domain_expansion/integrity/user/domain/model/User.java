package com.domain_expansion.integrity.user.domain.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.user.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.user.domain.model.vo.UserPhoneNumber;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Entity
@Table(name = "p_user")
@NoArgsConstructor(access = PROTECTED)
public class User extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Embedded
    @Column(name = "phone_number", unique = true, nullable = false)
    private UserPhoneNumber phoneNumber;

    @Column(name = "slack_id", unique = true, nullable = false)
    private String slackId;

    @ColumnDefault(value = "false")
    @Column(name = "is_delete", nullable = false)
    private boolean isDelete;

    @Builder(access = PRIVATE)
    public User(String username, UserRole role, UserPhoneNumber phoneNumber,
        String slackId) {
        this.username = username;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.slackId = slackId;
    }

    /**
     * 유저 생성
     */
    public static User from(String username, UserRole role, String phoneNumber, String slackId) {
        return User.builder()
            .username(username)
            .role(role)
            .phoneNumber(new UserPhoneNumber(phoneNumber))
            .slackId(slackId)
            .build();
    }

    /**
     * 비밀번호 생성
     */
    public void updatePassword(String encodedPassword) {
        this.password = encodedPassword;
    }


}
