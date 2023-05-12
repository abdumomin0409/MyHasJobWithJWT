package com.company.job.myhasjobwithjwt.controller;

import com.company.job.myhasjobwithjwt.domains.Chat;
import com.company.job.myhasjobwithjwt.payload.ResponseDTO;
import com.company.job.myhasjobwithjwt.payload.chat.MessageSendDTO;
import com.company.job.myhasjobwithjwt.repository.MessageRepository;
import com.company.job.myhasjobwithjwt.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.MESSAGE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(MESSAGE_URL)
public class MessageController {
    private final MessageService messageService;


}
