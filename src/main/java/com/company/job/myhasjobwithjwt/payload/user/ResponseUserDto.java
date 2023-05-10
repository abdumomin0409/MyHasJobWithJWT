package com.company.job.myhasjobwithjwt.payload.user;

import com.company.job.myhasjobwithjwt.domains.JobType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUserDto {
    private String id;
    private String fio;
    private String phoneNumber;
    private JobType job;
    private String photoUrl;
    private Integer experience;
    private int rate;
    private LocalDateTime createdAt;
}
