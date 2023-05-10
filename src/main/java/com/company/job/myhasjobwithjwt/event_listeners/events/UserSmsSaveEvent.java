package com.company.job.myhasjobwithjwt.event_listeners.events;

import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.domains.enums.SmsCodeType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class UserSmsSaveEvent {
    private final User user;
    private final SmsCodeType smsCodeType;
}
