package com.tutor.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutor.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
	
	Optional<Admin> findByEmail(String email);

    boolean existsByEmail(String email);

    // Traceability: SuperAdmin → Admin (`/.)
    List<Admin> findByCreatedBy_SuperAdminId(Long superAdminId);

    long count();
    
}

//    Admin createAdmin(Admin admin, Long superAdminId);
//
//    List<Teacher> getEntireTeachers(HttpServletRequest http);
//
//    List<?> getEntireStudents(HttpServletRequest request);


