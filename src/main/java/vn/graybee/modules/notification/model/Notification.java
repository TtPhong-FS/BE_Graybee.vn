package vn.graybee.modules.notification.model;


import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.graybee.modules.notification.enums.NotificationTarget;
import vn.graybee.modules.notification.enums.NotificationType;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
public class Notification {

    @Column(name = "created_at")
    private final LocalDateTime createdAt = LocalDateTime.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Enumerated(EnumType.STRING)
    private NotificationTarget target;

    @Column(name = "target_id")
    private Long targetId;

    private boolean read;

}

