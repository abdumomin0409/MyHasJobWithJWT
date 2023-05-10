package com.company.job.myhasjobwithjwt.payload.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springdoc.core.annotations.ParameterObject;

@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSmsDto {
    @NotNull(message = "Phone Number can not be null or empty ")
    @NotBlank(message = "Phone Number can not be null or empty ")
    @Size(min = 13, max = 13, message = "Phone Number must be {min} digits")
    private String phoneNumber;
    @NotBlank(message = "Code can not be null or empty ")
    @NotNull(message = "Code can not be null or empty ")
    @Size(min = 6, max = 6, message = "Code must be {min} digits")
    private String code;
}
