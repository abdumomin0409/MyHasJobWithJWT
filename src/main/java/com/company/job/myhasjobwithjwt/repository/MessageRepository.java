package com.company.job.myhasjobwithjwt.repository;

import com.company.job.myhasjobwithjwt.domains.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {


}