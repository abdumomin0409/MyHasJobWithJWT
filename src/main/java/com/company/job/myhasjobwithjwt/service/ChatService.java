package com.company.job.myhasjobwithjwt.service;

import com.company.job.myhasjobwithjwt.config.security.SessionUser;
import com.company.job.myhasjobwithjwt.domains.Chat;
import com.company.job.myhasjobwithjwt.domains.Message;
import com.company.job.myhasjobwithjwt.payload.chat.MessageSendDTO;
import com.company.job.myhasjobwithjwt.repository.ChatRepository;
import com.company.job.myhasjobwithjwt.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final SessionUser sessionUser;
    private final MessageRepository messageRepository;

    public Chat createChat(String toUserId) {
        String fromUserId = this.sessionUser.id();
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
        return this.chatRepository.save(build);
    }

    public Chat getChat(String toUserId, String fromUserId) {
        return this.chatRepository.findByFromUserIdAndToUserId(fromUserId, toUserId);
    }

    public Chat sendMessage(MessageSendDTO messageSendDTO) {
        String chatId = messageSendDTO.getChatId();
        Chat chat = this.chatRepository.findById(chatId).orElseThrow(() -> new RuntimeException("Chat not found"));
        String fromUserId = this.sessionUser.id();
        if (!fromUserId.equals(chat.getFromUserId()) && !fromUserId.equals(chat.getToUserId())) {
            throw new RuntimeException("You can't send message to this chat");
        }
        List<Message> messages = chat.getMessages();
        Message build = Message.builder()
                .userId(fromUserId)
                .text(messageSendDTO.getText())
                .build();
        Message save = messageRepository.save(build);
        messages.add(save);
        chat.setMessages(messages);
        return this.chatRepository.save(chat);
    }

    public Page<Chat> getAllChats(Pageable pageable) {
        String userId = this.sessionUser.id();
        return this.chatRepository.findAllByFromUserIdOrToUserId(userId, userId, pageable);
    }


}
