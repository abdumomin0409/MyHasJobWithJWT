package com.company.job.myhasjobwithjwt.payload.user;

import com.company.job.myhasjobwithjwt.costom_annotations.annotations.UniquePhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

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