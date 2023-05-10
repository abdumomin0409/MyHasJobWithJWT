package com.company.job.myhasjobwithjwt.service.auth;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
public class TwilioService {
    private String ACCOUNT_SID;
    private String AUTH_TOKEN;
    private String FROM_NUMBER;

    public void sendSMSCode(String toNumber, Integer code) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        String codeString = code/1000 + " " + code%1000 + " ";

        String messageBody = "Your Has Job verification code is: " + codeString;

        Message message = Message.creator(
                        new PhoneNumber(toNumber),
                        new PhoneNumber(FROM_NUMBER),
                        messageBody)
                .create();

        String sid = message.getSid();
        System.out.println(sid);
    }
}
