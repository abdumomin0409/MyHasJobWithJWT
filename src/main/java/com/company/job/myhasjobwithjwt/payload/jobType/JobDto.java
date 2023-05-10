package com.company.job.myhasjobwithjwt.payload.jobType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JobDto {
    @NotNull(message = "Old name should not be empty")
    @NotBlank(message = "Old name should not be empty")
    private String oldName;
    @NotNull(message = "New name should not be empty")
    @NotBlank(message = "New name should not be empty")
    private String newName;
}
