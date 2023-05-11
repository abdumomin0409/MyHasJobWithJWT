package com.company.job.myhasjobwithjwt.payload.user;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateDto {
    private String fio;
    private String phoneNumber;
    private String jobName;
    private String password;
    private String prePassword;
    private String photoUrl;
    private Integer experience;
    private int rate;
}