package com.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tutor.entity.Homework;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
}
