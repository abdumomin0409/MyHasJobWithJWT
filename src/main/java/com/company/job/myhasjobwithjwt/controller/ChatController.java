package com.company.job.myhasjobwithjwt.controller;

import com.company.job.myhasjobwithjwt.domains.Chat;
import com.company.job.myhasjobwithjwt.payload.ResponseDTO;
import com.company.job.myhasjobwithjwt.payload.chat.MessageSendDTO;
import com.company.job.myhasjobwithjwt.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.CHAT_URL;

@RestController
@RequestMapping(CHAT_URL)
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @GetMapping("/get/{toUserId}")
    public ResponseEntity<ResponseDTO<Chat>> createChat(@PathVariable String toUserId) {
        Chat chat = chatService.createChat(toUserId);
        return ResponseEntity.ok(new ResponseDTO<>("Chat created successfully", chat));
    }


    @PostMapping("/send")
    public ResponseEntity<ResponseDTO<Chat>> sendMessage(@RequestBody MessageSendDTO messageSendDTO) {
        Chat chat = chatService.sendMessage(messageSendDTO);
        return ResponseEntity.ok(new ResponseDTO<>("Chat created successfully", chat));
    }

}
