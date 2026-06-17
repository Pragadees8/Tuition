package com.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutor.entity.SubAdmin;

import java.util.List;
import java.util.Optional;


@Repository
public interface SubAdminRepository extends JpaRepository<SubAdmin, Long> {

    Optional<SubAdmin> findByName(String name);
    
    Optional<SubAdmin> findByEmail(String email);

    boolean existsByEmail(String email);

    // Traceability: SuperAdmin → SubAdmin(`/.)
    List<SubAdmin> findByCreatedBySuperAdminId(Long superAdminId);

    long count();
}
