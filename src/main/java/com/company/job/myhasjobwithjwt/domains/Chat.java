package com.company.job.myhasjobwithjwt.domains;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Chat extends Auditable {

    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    private User fromUser;

    @ManyToOne
    private User toUser;

    @Builder.Default
    private boolean isActive = true;

}
