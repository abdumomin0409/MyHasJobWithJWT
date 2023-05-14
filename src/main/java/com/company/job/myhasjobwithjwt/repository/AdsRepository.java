package com.company.job.myhasjobwithjwt.repository;

import com.company.job.myhasjobwithjwt.domains.Ads;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdsRepository extends JpaRepository<Ads, String> {

    @Query("select a from Ads a where a.isActive = true and a.title = ?1 and a.price = ?2")
    Optional<Ads> findByTitleAndPrice(String title, Double price);

    @Query("select a from Ads a where a.isActive = true and a.id = ?1")
    Optional<Ads> findId(String id);

    @Query("select a from Ads a where a.isActive = true")
    Page<Ads> findAllActive(Pageable pageable);

    @Query("select a from Ads a ")
    Page<Ads> findAllFixed(Pageable pageable);



}