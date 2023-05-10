package com.company.job.myhasjobwithjwt.service.auth;

import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.domains.UserSms;
import com.company.job.myhasjobwithjwt.domains.enums.SmsCodeType;
import com.company.job.myhasjobwithjwt.event_listeners.events.GoingTwilioEvent;
import com.company.job.myhasjobwithjwt.repository.UserSmsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserSmsService {
    private final UserSmsRepository userSmsRepository;
    private final Random random;
    private final TwilioService twilioService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void createUserSms(User savedUser, SmsCodeType type) {
        String userId = savedUser.getId();
        UserSms userSms = findByUserId(userId, type);
        if (!Objects.isNull(userSms)) {
            return;
        }
        UserSms build = UserSms.builder()
                .userId(savedUser.getId())
                .randomCode(randomCode())
                .toTime(LocalDateTime.now().plus(3, ChronoUnit.MINUTES))
                .build();
        build.setExpired(false);
        userSmsRepository.save(build);
        applicationEventPublisher.publishEvent(new GoingTwilioEvent(savedUser.getPhoneNumber(), build.getRandomCode()));
    }


    public UserSms findByUserId(String userId, SmsCodeType type) {
        return userSmsRepository.findByUserId(userId, type);
    }

    private Integer randomCode() {
        return random.nextInt(999_999 - 100_000) + 100_000;
    }

    public void updatedExpired() {
        userSmsRepository.updateExpired();
    }

    public void update(UserSms userSms) {
        userSmsRepository.save(userSms);
    }
}
