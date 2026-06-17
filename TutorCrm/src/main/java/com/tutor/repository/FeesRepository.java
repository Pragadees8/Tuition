package com.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tutor.entity.Fees;

public interface FeesRepository extends JpaRepository<Fees, Long> {
}
