package com.tutor.repository;

import com.tutor.entity.Student;
import com.tutor.enums.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
    boolean existsByParentMobile(String parentMobile);

    List<Student> findByAdminAdminId(Long adminId);

    Optional<Student> findByIdAndAdminAdminId(Long id, Long adminId);

    long countByRollNumberStartingWith(String prefix);

    // ✅ FIND BY ROLL NUMBER
    Optional<Student> findByRollNumber(String rollNumber);

    // ✅ FIND BY SECTION
    List<Student> findBySection(Section section);

    // ✅ FIND BY ROLL NUMBER AND SECTION
    Optional<Student> findByRollNumberAndSection(String rollNumber, Section section);
}