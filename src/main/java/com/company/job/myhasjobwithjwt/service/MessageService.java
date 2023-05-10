package com.company.job.myhasjobwithjwt.service;

import com.company.job.myhasjobwithjwt.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;



}
