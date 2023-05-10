package com.company.job.myhasjobwithjwt.repository;

import com.company.job.myhasjobwithjwt.domains.UserSms;
import com.company.job.myhasjobwithjwt.domains.enums.SmsCodeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserSmsRepository extends JpaRepository<UserSms, Integer> {
    @Query("select u from UserSms u where u.expired = false and u.type = ?2 and u.userId = ?1 and u.toTime > CURRENT_TIMESTAMP")
    UserSms findByUserId(String userId, SmsCodeType type);


    @Transactional
    @Modifying
    @Query("update UserSms u set u.expired = true where u.toTime < CURRENT_TIMESTAMP")
    void updateExpired();
}