package com.company.job.myhasjobwithjwt.repository;

import com.company.job.myhasjobwithjwt.domains.JobType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.sax.SAXResult;
import java.util.List;
import java.util.Optional;

public interface JobTypeRepository extends JpaRepository<JobType, Integer> {
    @Query("select (count(j) > 0) from JobType j where j.isActive = true and upper(j.name) = upper(?1)")
    boolean existsByIsActiveTrueAndName(String name);

    @Query("select j from JobType j where j.isActive = true and j.name = ?1")
    Optional<JobType> findByName(String jobName);

    @Query("select j from JobType j where j.isActive = true")
    List<JobType> findAllByIsActiveTrue();

    @Transactional
    @Modifying
    @Query("update JobType j set j.name = ?1 where j.isActive = true and j.name = ?2")
    void updateByActiveTrueAndName(String newName, String oldName);

    @Transactional
    @Modifying
    @Query("update JobType j set j.isActive = false where j.id = ?1")
    void deleteById(Integer id);
}