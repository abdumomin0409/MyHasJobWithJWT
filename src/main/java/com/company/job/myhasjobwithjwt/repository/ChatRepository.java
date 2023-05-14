package com.company.job.myhasjobwithjwt.repository;

import com.company.job.myhasjobwithjwt.domains.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat, String> {


    @Query("select c from Chat c where c.deleted = false and ((c.fromUserId = ?1 and c.toUserId = ?2) or (c.fromUserId = ?2 and c.toUserId = ?1))")
    Chat findByFromUserIdAndToUserId(String fromUserId, String toUserId);

    @Query("select c from Chat c where c.fromUserId = ?1 or c.toUserId = ?2")
    Page<Chat> findAllByFromUserIdOrToUserId(String userId, String userId1, Pageable pageable);
}