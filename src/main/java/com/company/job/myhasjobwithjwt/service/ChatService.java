package com.company.job.myhasjobwithjwt.service;

import com.company.job.myhasjobwithjwt.config.security.SessionUser;
import com.company.job.myhasjobwithjwt.domains.Chat;
import com.company.job.myhasjobwithjwt.domains.Message;
import com.company.job.myhasjobwithjwt.payload.chat.MessageSendDTO;
import com.company.job.myhasjobwithjwt.repository.ChatRepository;
import com.company.job.myhasjobwithjwt.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final SessionUser sessionUser;
    private final MessageRepository messageRepository;

    public Chat createChat(String toUserId) {
        String fromUserId = sessionUser.id();
        if (fromUserId.equals(toUserId)) {
            throw new RuntimeException("You can't chat with yourself");
        }
        Chat chat = getChat(toUserId, fromUserId);
        if (chat != null) {
            return chat;
        }
        Chat build = Chat.builder()
                .fromUserId(fromUserId)
                .toUserId(toUserId)
                .build();
        return chatRepository.save(build);
    }

    public Chat getChat(String toUserId, String fromUserId) {
        return chatRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);
    }

    public Chat sendMessage(MessageSendDTO messageSendDTO) {
        String chatId = messageSendDTO.getChatId();
        Chat chat = chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        List<Message> messages = chat.getMessages();
        String fromUserId = messageSendDTO.getUserId();
        Message build = Message.builder()
                .userId(fromUserId)
                .text(messageSendDTO.getText())
                .build();
        Message save = messageRepository.save(build);
        messages.add(save);
        chat.setMessages(messages);
        return chatRepository.save(chat);
    }



}
