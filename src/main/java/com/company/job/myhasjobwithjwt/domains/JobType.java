package com.company.job.myhasjobwithjwt.domains;

import jakarta.persistence.*;
import lombok.*;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class JobType extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String name;

    private boolean isActive;
}
