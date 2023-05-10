package com.company.job.myhasjobwithjwt.event_listeners.listeners;

import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.domains.enums.SmsCodeType;
import com.company.job.myhasjobwithjwt.event_listeners.events.GoingTwilioEvent;
import com.company.job.myhasjobwithjwt.event_listeners.events.UserSmsSaveEvent;
import com.company.job.myhasjobwithjwt.service.auth.TwilioService;
import com.company.job.myhasjobwithjwt.service.auth.UserSmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@EnableAsync
public class UserSmsListener {

    private final UserSmsService userSmsService;
    private final TwilioService twilioService;

    @Async
    @EventListener(value = UserSmsSaveEvent.class)
    public void userSmsSaveEventListener(UserSmsSaveEvent event) {
        User user = event.getUser();
        SmsCodeType smsCodeType = event.getSmsCodeType();
        if (user != null) {
            userSmsService.createUserSms(user, smsCodeType);
        }
    }

    @Async
    @EventListener(value = GoingTwilioEvent.class)
    public void goingTwilioEventListener(GoingTwilioEvent event) {
        String toPhoneNumber = event.getToPhoneNumber();
        Integer randomCode = event.getRandomCode();
        if (toPhoneNumber != null && randomCode != null) {
            System.out.println(" = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = ");
            System.out.println(" = = = =                                                         = = = ");
            System.out.println(" = = = =     " + randomCode + "                                          = = = ");
            System.out.println(" = = = =                                                         = = = ");
            System.out.println(" = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = ");
//            twilioService.sendSMSCode(toPhoneNumber, randomCode);
        }
    }

}
