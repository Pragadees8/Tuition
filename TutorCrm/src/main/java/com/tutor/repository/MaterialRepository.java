package com.tutor.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tutor.entity.Material;

public interface MaterialRepository extends JpaRepository<Material, Long> {
}
