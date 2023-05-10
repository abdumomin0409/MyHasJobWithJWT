package com.company.job.myhasjobwithjwt.payload.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignInDto {
    @NotNull(message = "phoneNumber can not be null")
    @NotBlank(message = "phoneNumber can not be blank")
    @Size(min = 13, max = 13, message = "phoneNumber must be 13 characters")
    private String phoneNumber;
    @NotNull(message = "password can not be null")
    @NotBlank(message = "password can not be blank")
    @Size(min = 8, max = 16, message = "password must be between {min} and {max}")
    private String password;
}
