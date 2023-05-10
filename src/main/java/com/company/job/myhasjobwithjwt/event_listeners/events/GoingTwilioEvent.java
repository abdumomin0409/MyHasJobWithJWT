package com.company.job.myhasjobwithjwt.event_listeners.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class GoingTwilioEvent {
    private final String toPhoneNumber;
    private final Integer randomCode;
}
