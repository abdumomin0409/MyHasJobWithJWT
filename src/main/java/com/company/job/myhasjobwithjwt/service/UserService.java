package com.company.job.myhasjobwithjwt.service;

import com.company.job.myhasjobwithjwt.config.security.SessionUser;
import com.company.job.myhasjobwithjwt.domains.JobType;
import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.domains.enums.UserStatus;
import com.company.job.myhasjobwithjwt.payload.user.ResponseUserDto;
import com.company.job.myhasjobwithjwt.payload.user.UserUpdateDto;
import com.company.job.myhasjobwithjwt.repository.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.company.job.myhasjobwithjwt.mappers.UserMapper.USER_MAPPER;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SessionUser sessionUser;
    private final PasswordEncoder encoder;
    private final JobTypeService jobTypeService;

    public UserService(UserRepository userRepository, SessionUser sessionUser, PasswordEncoder encoder, @Lazy JobTypeService jobTypeService) {
        this.userRepository = userRepository;
        this.sessionUser = sessionUser;
        this.encoder = encoder;
        this.jobTypeService = jobTypeService;
    }

    public User currentUser() {
        return userRepository.findById(sessionUser.id())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public ResponseUserDto getProfile() {
        User user = currentUser();
        ResponseUserDto dto = USER_MAPPER.toDto(user);
        dto.setJob(user.getJob());
        return dto;
    }

    public User update(UserUpdateDto dto) {
        String dtoPassword = dto.getPassword();
        if (!dtoPassword.equals(dto.getPrePassword())) {
            throw new RuntimeException("Passwords are not equal");
        }
        if (userRepository.existsByPhoneNumber(UserStatus.DELETED,  dto.getPhoneNumber())) {
            throw new RuntimeException("User with this phone number already exists");
        }
        JobType jobType = jobTypeService.findByName(dto.getJobName());
        if (Objects.isNull(jobType)) {
            throw new RuntimeException("Job type not found!");
        }
        User user = currentUser();
        dto.setPassword(encoder.encode(dtoPassword));
        USER_MAPPER.updateUsersFromDTO(dto, user);
        user.setJob(jobType);
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }


    public User findJobType(JobType jobType) {
        return userRepository.findByJob(UserStatus.DELETED, jobType);
    }
}
