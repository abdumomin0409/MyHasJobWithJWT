package com.company.job.myhasjobwithjwt.service;

import com.company.job.myhasjobwithjwt.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;




}
