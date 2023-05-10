package com.company.job.myhasjobwithjwt.domains;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Message extends Auditable {
    @Id
    @UuidGenerator
    private String id;
    @Column(nullable = false)
    private String text;
    @ManyToOne
    private Chat chat;
    @ManyToOne
    private User user;
    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();
    @Builder.Default
    private boolean isActive = true;

}
