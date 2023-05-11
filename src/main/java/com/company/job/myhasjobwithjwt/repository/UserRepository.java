package com.company.job.myhasjobwithjwt.repository;

import com.company.job.myhasjobwithjwt.domains.JobType;
import com.company.job.myhasjobwithjwt.domains.User;
import com.company.job.myhasjobwithjwt.domains.enums.UserStatus;
import com.company.job.myhasjobwithjwt.payload.user.ResponseUserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    @Transactional
    @Modifying
    @Query("update users u set u.role = com.company.job.myhasjobwithjwt.domains.enums.UserRole.ADMIN , u.status = com.company.job.myhasjobwithjwt.domains.enums.UserStatus.ACTIVE where u.phoneNumber = ?1")
    void promoteToSuperAdmin(String superAdmin1);

    @Query("select u from users u where u.phoneNumber = ?1")
    Optional<User> optionalFindByPhoneNumber(String s);

    @Query("select u from users u where u.phoneNumber = ?1")
    User findByPhoneNumber(String s);

    @Query("select (count(u) > 0) from users u where u.status <> ?1 and u.phoneNumber = ?2")
    boolean existsByPhoneNumber(UserStatus status , String s);

    @Query("select u from users u where u.status = ?1 and u.job <> ?2")
    List<User> findAllByStatus(UserStatus userStatus, JobType jobType);

    @Transactional
    @Modifying
    @Query("update users u set u.status = ?1 where u.id = ?2")
    void updateStatusById(UserStatus userStatus, String id);

}