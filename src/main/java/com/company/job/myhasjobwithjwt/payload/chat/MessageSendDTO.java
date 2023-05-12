package com.company.job.myhasjobwithjwt.payload.chat;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageSendDTO {
    private String text;
    private String userId;
    private String chatId;
}
