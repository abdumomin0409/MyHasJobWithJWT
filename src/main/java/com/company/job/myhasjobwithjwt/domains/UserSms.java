package com.company.job.myhasjobwithjwt.domains;

import com.company.job.myhasjobwithjwt.domains.enums.SmsCodeType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class UserSms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int randomCode;

    private String userId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private SmsCodeType type = SmsCodeType.ACTIVATION;

    @CreationTimestamp
    @Column(columnDefinition = "timestamp default now()", updatable = false)
    private LocalDateTime fromTime;

    @Column(columnDefinition = "timestamp default now()+INTERVAL '3 Minutes'")
    private LocalDateTime toTime;

    private boolean expired;
}
