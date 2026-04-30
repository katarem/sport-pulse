package com.bytecodes.entity;

import com.bytecodes.model.NotificationChannel;
import com.bytecodes.model.NotificationEvent;
import com.bytecodes.model.SubscriptionStatus;
import com.bytecodes.model.SubscriptionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.data.annotation.CreatedDate;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Entity(name = "subscriptions")
@Getter
@Setter
@EqualsAndHashCode
public class SubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID userId;
    private SubscriptionType type;
    private Integer teamId;
    private Integer fixtureId;
    @Enumerated(EnumType.STRING)
    private Set<NotificationEvent> events;
    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;
    private String webhookUrl;
    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;
    @CreatedDate
    private Instant createdAt;
}
