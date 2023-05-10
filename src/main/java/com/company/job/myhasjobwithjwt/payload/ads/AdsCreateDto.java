package com.company.job.myhasjobwithjwt.payload.ads;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdsCreateDto {
    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotBlank(message = "Address should not be blank")
    private String address;

    @NotBlank(message = "Latitude should not be blank")
    private String latitude;

    @NotBlank(message = "Longitude should not be blank")
    private String longitude;

    private String description;

    private Double price;

}
