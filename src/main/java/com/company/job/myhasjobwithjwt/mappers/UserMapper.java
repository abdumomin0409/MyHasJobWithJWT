package com.company.job.myhasjobwithjwt.mappers;

import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.payload.user.ResponseUserDto;
import com.company.job.myhasjobwithjwt.payload.user.UserSignUpDto;
import com.company.job.myhasjobwithjwt.payload.user.UserUpdateDto;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    User toEntity(UserSignUpDto dto);

    ResponseUserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    void updateUsersFromDTO(UserUpdateDto dto, User user);


}
