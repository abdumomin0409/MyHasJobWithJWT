package com.company.job.myhasjobwithjwt.payload.user;

import com.company.job.myhasjobwithjwt.costom_annotations.annotations.UniquePhoneNumber;
import jakarta.validation.constraints.Email;
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
public class UserSignUpDto {

    @NotBlank(message = "fio bo'sh bo'lmasligi kerak")
    private String fio;

    @NotBlank(message = "phoneNumber bo'sh bo'lmasligi kerak")
    @UniquePhoneNumber(message = "Bu phoneNumber avval ro'yxatdan o'tgan")
    private String phoneNumber;

    @NotBlank(message = "password bo'sh bo'lmasligi kerak")
    @Size(min = 8, message = "password 8 ta belgidan kam bo'lmasligi kerak")
    private String password;

    @NotBlank(message = "prePassword bo'sh bo'lmasligi kerak")
    @Size(min = 8, message = "prePassword 8 ta belgidan kam bo'lmasligi kerak")
    private String prePassword;

    @NotNull(message = "jobName bo'sh bo'lmasligi kerak")
    @NotBlank(message = "jobName bo'sh bo'lmasligi kerak")
    private String jobName;
}
