package com.company.job.myhasjobwithjwt.domains;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ads extends Auditable {
    @Id
    @UuidGenerator
    private String id;

    @ManyToOne
    private User user;

    private String address;

    @Column(nullable = false)
    private String latitude;

    @Column(nullable = false)
    private String longitude;

    private String description;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false, length = 50)
    private String title;

    @Builder.Default
    private boolean isActive = true;
}
