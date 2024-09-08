package com.domain_expansion.integrity.user.domain.model;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.user.common.entity.BaseDateEntity;
import com.domain_expansion.integrity.user.presentation.request.UserCreateRequestDto;
import jakarta.persistence.Column;
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

    @Column(unique = true)
    private String username;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole role;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "slack_id", unique = true)
    private String slackId;

    @ColumnDefault(value = "false")
    @Column(name = "is_delete")
    private boolean isDelete;

    @Builder(access = PRIVATE)
    public User(String username, String password, UserRole role, String phoneNumber,
        String slackId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.phoneNumber = phoneNumber;
        this.slackId = slackId;
    }

    /**
     * 유저 생성
     */
    public static User from(UserCreateRequestDto requestDto, String encodedPassword) {
        return User.builder()
            .username(requestDto.username())
            .password(encodedPassword)
            .role(requestDto.role())
            .phoneNumber(requestDto.phoneNumber())
            .slackId(requestDto.slackId())
            .build();
    }


}
