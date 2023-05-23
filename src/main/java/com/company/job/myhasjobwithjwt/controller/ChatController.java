package com.company.job.myhasjobwithjwt.controller;

import com.company.job.myhasjobwithjwt.domains.Chat;
import com.company.job.myhasjobwithjwt.payload.ResponseDTO;
import com.company.job.myhasjobwithjwt.payload.chat.MessageSendDTO;
import com.company.job.myhasjobwithjwt.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.company.job.myhasjobwithjwt.utils.BaseUrls.CHAT_URL;

@RestController
@RequestMapping(CHAT_URL)
@RequiredArgsConstructor
@PreAuthorize("isAuthenticated()")
@Tag(name = "Chat", description = "Chat API")
public class ChatController {
    private final ChatService chatService;

    @Operation(summary = "This API is used for create chat", responses = {
            @ApiResponse(responseCode = "201", description = "Chat created", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/create/{toUserId}")
    public ResponseEntity<ResponseDTO<Chat>> createChat(@PathVariable String toUserId) {
        Chat chat = this.chatService.createChat(toUserId);
        return ResponseEntity.ok(new ResponseDTO<>("Chat created successfully", chat));
    }


    @Operation(summary = "This API is used for sending message by chat", responses = {
            @ApiResponse(responseCode = "200", description = "Message sent", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @PostMapping("/send/message")
    public ResponseEntity<ResponseDTO<Chat>> sendMessage(@RequestBody MessageSendDTO messageSendDTO) {
        Chat chat = this.chatService.sendMessage(messageSendDTO);
        return ResponseEntity.ok(new ResponseDTO<>("Message sent", chat));
    }


    @Operation(summary = "This API is used for get all chats", responses = {
            @ApiResponse(responseCode = "200", description = "Returned all chats", content = @Content(schema = @Schema(implementation = ResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ResponseDTO.class)))})
    @GetMapping("/get/all")
    public ResponseEntity<ResponseDTO<Page<Chat>>> getAllChats(@RequestParam(required = false, defaultValue = "10") Integer size,
                                                               @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Chat> chats = this.chatService.getAllChats(pageable);
        return ResponseEntity.ok(new ResponseDTO<>("Chat created successfully", chats));
    }
}
