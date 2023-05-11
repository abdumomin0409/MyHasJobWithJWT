package com.company.job.myhasjobwithjwt.service.auth;

import com.company.job.myhasjobwithjwt.config.security.JwtUtils;
import com.company.job.myhasjobwithjwt.domains.JobType;
import com.company.job.myhasjobwithjwt.domains.enums.SmsCodeType;
import com.company.job.myhasjobwithjwt.domains.enums.UserStatus;
import com.company.job.myhasjobwithjwt.event_listeners.events.UserSmsSaveEvent;
import com.company.job.myhasjobwithjwt.payload.auth.*;
import com.company.job.myhasjobwithjwt.payload.user.*;
import com.company.job.myhasjobwithjwt.service.JobTypeService;
import com.company.job.myhasjobwithjwt.domains.enums.TokenType;
import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.domains.UserSms;
import com.company.job.myhasjobwithjwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.company.job.myhasjobwithjwt.mappers.UserMapper.USER_MAPPER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserSmsService userSmsService;
    private final JobTypeService jobTypeService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public User signUp(UserSignUpDto dto) {
        if (!dto.getPassword().equals(dto.getPrePassword())) {
            throw new RuntimeException("Passwords are not equal");
        }
        JobType jobType = jobTypeService.findByName(dto.getJobName());
        if (!dto.getPhoneNumber().matches("^[+][0-9]{12}$")) {
            throw new RuntimeException("Invalid phone number");
        }
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        User user = USER_MAPPER.toEntity(dto);
        user.setJob(jobType);
        user.setStatus(UserStatus.NO_ACTIVE);
        User saved = saveToDb(user);
        applicationEventPublisher.publishEvent(new UserSmsSaveEvent(saved, SmsCodeType.ACTIVATION));
        return saved;
    }

    public String activate(UserSmsDto dto) {
        User user = findByPhone(dto.getPhoneNumber());
        UserSms userSms = userSmsService.findByUserId(user.getId(), SmsCodeType.ACTIVATION);
        if (!Objects.isNull(userSms) && userSms.getRandomCode() == Integer.parseInt(dto.getCode())) {
            userSms.setExpired(true);
            userSmsService.update(userSms);
            userRepository.updateStatusById(UserStatus.ACTIVE, user.getId());
            return "User successfully activated";
        }
        throw new RuntimeException("Invalid code");
    }

    public TokenResponse getAccessToken(UserSignInDto dto) {
        String phoneNumber = dto.getPhoneNumber();
        String password = dto.getPassword();
        User user = findByPhone(phoneNumber);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Phone number or password is incorrect");
        }
        userRepository.updateStatusById(UserStatus.ACTIVE, user.getId());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(phoneNumber, password);
        this.authenticationManager.authenticate(authentication);
        return jwtUtils.generateToken(phoneNumber);
    }

    public TokenResponse refreshToken(RefreshTokenRequest dto) {
        String refreshToken = dto.getRefreshToken();
        if (!jwtUtils.isTokenValid(refreshToken, TokenType.REFRESH)) {
            throw new RuntimeException("Refresh token is invalid");
        }
        String phoneNumber = jwtUtils.getUsername(refreshToken, TokenType.REFRESH);
        userRepository.optionalFindByPhoneNumber(phoneNumber);
        TokenResponse tokenResponse = TokenResponse.builder()
                .refreshToken(refreshToken)
                .refreshTokenExpiry(jwtUtils.getExpiry(refreshToken, TokenType.REFRESH))
                .build();
        return jwtUtils.generateAccessToken(phoneNumber, tokenResponse);
    }

    public List<ResponseUserDto> getAllActive() {
        JobType byName = jobTypeService.findByName("Ish beruvchi");
        List<ResponseUserDto> all = new ArrayList<>();
        List<User> userList = userRepository.findAllByStatus(UserStatus.ACTIVE, byName);
        userList.forEach(user -> {
            ResponseUserDto responseUserDto = ResponseUserDto.builder()
                    .id(user.getId())
                    .fio(user.getFio())
                    .phoneNumber(user.getPhoneNumber())
                    .job(user.getJob())
                    .experience(user.getExperience())
                    .photoUrl(user.getPhotoUrl())
                    .rate(user.getRate())
                    .createdAt(user.getCreatedAt())
                    .build();
            all.add(responseUserDto);
        });
        return all;
    }

    public void resendCode(String phoneNumber, SmsCodeType smsCodeType) {
        User user = findByPhone(phoneNumber);
        applicationEventPublisher.publishEvent(new UserSmsSaveEvent(user, smsCodeType));
    }

    public void updateSuperAdmin(String superAdmin1) {
        User user = userRepository.findByPhoneNumber(superAdmin1);
        if (!Objects.isNull(user)) {
            userRepository.promoteToSuperAdmin(superAdmin1);
        }
    }


    public void forgetPasswordActivate(UserResetPasswordDTO dto) {
        User user = findByPhone(dto.phoneNumber());
        UserSms userSms = userSmsService.findByUserId(user.getId(), SmsCodeType.FORGET_PASSWORD);
        if (!Objects.isNull(userSms) && userSms.getRandomCode() == Integer.parseInt(dto.code())) {
            userSms.setExpired(true);
            userSmsService.update(userSms);
            user.setPassword(passwordEncoder.encode(dto.password()));
            userRepository.save(user);
            return;
        }
        throw new RuntimeException("Invalid code");
    }

    private User findByPhone(String phoneNumber) {
        return userRepository.optionalFindByPhoneNumber(phoneNumber).orElseThrow(() -> new RuntimeException("Phone number not found"));
    }

    public boolean existsByPhoneNumber(String s) {
        return userRepository.existsByPhoneNumber(s);
    }

    public User saveToDb(User user) {
        return userRepository.save(user);
    }

}
