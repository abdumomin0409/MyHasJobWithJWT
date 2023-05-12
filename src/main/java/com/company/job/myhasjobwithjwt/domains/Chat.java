package com.company.job.myhasjobwithjwt.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Chat extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    private String fromUserId;

    private String toUserId;

    @OneToMany
    private List<Message> messages;

    @Builder.Default
    private boolean deleted = false;
}
