package com.company.job.myhasjobwithjwt.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Message {
    @Id
    @UuidGenerator
    private String id;

    @Column(nullable = false)
    private String text;

    private String userId;

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    @Builder.Default
    @JsonIgnore
    private boolean deleted = false;
}
