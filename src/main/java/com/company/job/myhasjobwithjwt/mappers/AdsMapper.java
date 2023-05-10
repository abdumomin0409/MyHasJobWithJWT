package com.company.job.myhasjobwithjwt.mappers;

import com.company.job.myhasjobwithjwt.domains.Ads;
import com.company.job.myhasjobwithjwt.payload.ads.AdsCreateDto;
import com.company.job.myhasjobwithjwt.payload.ads.AdsUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdsMapper {

    AdsMapper ADS_MAPPER = Mappers.getMapper(AdsMapper.class);

    Ads toEntity(AdsCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateAdsFromDTO(AdsUpdateDto dto, @MappingTarget Ads ads);

}
