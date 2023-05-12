package com.company.job.myhasjobwithjwt.repository;

import com.company.job.myhasjobwithjwt.domains.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat, String> {


    @Query("select c from Chat c where c.deleted = false and ((c.fromUserId = ?1 and c.toUserId = ?2) or (c.fromUserId = ?2 and c.toUserId = ?1))")
    Chat findByFromUserIdAndToUserId(String fromUserId, String toUserId);
}