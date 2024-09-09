package com.domain_expansion.integrity.slack.domain.model;

import static lombok.AccessLevel.PROTECTED;

import com.domain_expansion.integrity.slack.common.entity.BaseDateEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Getter
@Table(name = "p_slack")
@Entity
public class Slack extends BaseDateEntity {

    @Id
    @Column(name = "slack_id")
    private String id;

    @Column(name = "receive_id")
    private Long receiveId;

    @Column
    private String message;

    @Column(name = "send_time")
    private LocalDateTime sendTime;

    @Builder(access = AccessLevel.PRIVATE)
    private Slack(String id, Long receivedId, String message) {
        this.id = id;
        this.receiveId = receivedId;
        this.message = message;
        this.sendTime = LocalDateTime.now();
    }


    public static Slack from(String id, Long receiveId, String message) {
        return Slack.builder()
            .id(id)
            .receivedId(receiveId)
            .message(message)
            .build();
    }
}
