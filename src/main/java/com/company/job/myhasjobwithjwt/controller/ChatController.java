package com.company.job.myhasjobwithjwt.controller;

import com.company.job.myhasjobwithjwt.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.CHAT_URL;

@RestController
@RequestMapping(CHAT_URL)
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;



}
