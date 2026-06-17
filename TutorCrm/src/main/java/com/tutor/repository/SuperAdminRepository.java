package com.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutor.entity.SuperAdmin;

import java.util.Optional;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {

    boolean existsByEmail(String email);

    Optional<SuperAdmin> findByEmail(String email);

   // Optional<SuperAdmin> findByFullName(String name);
}
