package com.company.job.myhasjobwithjwt.repository;

import com.company.job.myhasjobwithjwt.domains.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, String> {


}