package com.company.job.myhasjobwithjwt.payload.ads;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdsUpdateDto {
    private String id;
    private String title;
    private String address;
    private String latitude;
    private String longitude;
    private String description;
    private Double price;
}
