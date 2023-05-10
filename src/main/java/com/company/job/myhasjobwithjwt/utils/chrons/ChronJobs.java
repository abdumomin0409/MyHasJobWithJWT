package com.company.job.myhasjobwithjwt.utils.chrons;

import com.company.job.myhasjobwithjwt.service.auth.AuthService;
import com.company.job.myhasjobwithjwt.service.auth.UserSmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class ChronJobs {

    @Value("+998930815351")
    private String superAdmin1;

    private final AuthService authService;
    private final UserSmsService userSmsService;

    @Async
    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.MINUTES)
    public void updateSMSWithTime() {
        userSmsService.updatedExpired();
    }

    @Async
    @Scheduled(initialDelay = 1, fixedDelay = 10 * 24 * 60, timeUnit = TimeUnit.MINUTES)
    public void checkForSuperAdmins() {
        authService.promoteToSuperAdmin(superAdmin1);
    }


}
