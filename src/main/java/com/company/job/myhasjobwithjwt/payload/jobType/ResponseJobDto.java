package com.company.job.myhasjobwithjwt.payload.jobType;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseJobDto {
    private Integer id;
    private String name;
}
