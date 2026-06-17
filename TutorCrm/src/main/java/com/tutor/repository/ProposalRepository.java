package com.tutor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tutor.entity.Proposal;
import com.tutor.enums.Status;

@Repository
public interface ProposalRepository extends JpaRepository<Proposal, Long> {

//    // SuperAdmin – view all pending proposals
//    List<Proposal> findByStatus(Status status);
//
//    // Admin – view proposals created by admin
//    List<Proposal> findByAdminId(Long adminId);
//
//    // SubAdmin – view proposals created by subadmin
//    List<Proposal> findBySubAdminId(Long subAdminId);

	    // SuperAdmin – view all pending proposals
	    List<Proposal> findByStatus(Status status);

	    // Admin – view proposals created by admin
	    @Query("SELECT p FROM Proposal p WHERE p.admin.adminId = :adminId")
	    List<Proposal> findByAdminId(@Param("adminId") Long adminId);

	    // SubAdmin – view proposals created by subadmin
	    @Query("SELECT p FROM Proposal p WHERE p.subAdmin.id = :subAdminId")
	    List<Proposal> findBySubAdminId(@Param("subAdminId") Long subAdminId);
	}


