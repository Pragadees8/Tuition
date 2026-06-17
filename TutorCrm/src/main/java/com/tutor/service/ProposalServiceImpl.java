package com.tutor.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutor.config.SecurityUtil;
import com.tutor.dto.ProposalRequestDTO;
import com.tutor.dto.ProposalResponseDTO;
import com.tutor.dto.ProposalReviewDTO;
import com.tutor.entity.Admin;
import com.tutor.entity.Proposal;
import com.tutor.entity.SubAdmin;
import com.tutor.entity.SuperAdmin;
import com.tutor.enums.Role;
import com.tutor.enums.ProposalType;
import com.tutor.enums.Status;
import com.tutor.repository.AdminRepository;
import com.tutor.repository.ProposalRepository;
import com.tutor.repository.SubAdminRepository;
import com.tutor.repository.SuperAdminRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProposalServiceImpl implements ProposalService {

    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private SubAdminRepository subAdminRepository;
    @Autowired
    private SuperAdminRepository superAdminRepository;

    /* ================= CREATE PROPOSAL ================= */
    @Override
    public ProposalResponseDTO createProposal(ProposalRequestDTO requestDTO) {

        Long userId;
        Role role;

        // Use manual values if provided (bypass token)
        if (requestDTO.getUserId() != null && requestDTO.getRole() != null) {
            userId = requestDTO.getUserId();
            role = requestDTO.getRole();
        } else {
            // Fallback to SecurityUtil (JWT token)
            userId = SecurityUtil.getCurrentUserId();
            role = SecurityUtil.getCurrentUserRole();
        }

        Proposal proposal = new Proposal();
        proposal.setTitle(requestDTO.getTitle());
        proposal.setDescription(requestDTO.getDescription());
        proposal.setProposalType(ProposalType.valueOf(requestDTO.getProposalType()));
        proposal.setStatus(Status.PENDING);
        proposal.setCreatedAt(LocalDateTime.now());

        if (role == Role.ADMIN) {
            Admin admin = adminRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("Admin not found with id: " + userId));
            proposal.setAdmin(admin);
        } else if (role == Role.SUB_ADMIN) {
            SubAdmin subAdmin = subAdminRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("SubAdmin not found with id: " + userId));
            proposal.setSubAdmin(subAdmin);
        } else {
            throw new RuntimeException("Only Admin or SubAdmin can create proposals");
        }

        return mapToDTO(proposalRepository.save(proposal));
    }

    /* ================= VIEW OWN PROPOSALS ================= */
    @Override
    public List<ProposalResponseDTO> getMyProposals(Long userId, Role role) {

        if (userId == null || role == null) {
            userId = SecurityUtil.getCurrentUserId();
            role = SecurityUtil.getCurrentUserRole();
        }

        List<Proposal> proposals;

        if (role == Role.ADMIN) {
            proposals = proposalRepository.findByAdminId(userId);
        } else if (role == Role.SUB_ADMIN) {
            proposals = proposalRepository.findBySubAdminId(userId);
        } else {
            throw new RuntimeException("Invalid role");
        }

        return proposals.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /* ================= SUPERADMIN VIEW ================= */
    @Override
    public List<ProposalResponseDTO> getPendingProposals() {
        return proposalRepository.findByStatus(Status.PENDING).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /* ================= REVIEW PROPOSAL ================= */
    @Override
    public ProposalResponseDTO reviewProposal(Long proposalId, ProposalReviewDTO reviewDTO) {

        Long superAdminId = SecurityUtil.getCurrentUserId();
        Role role = SecurityUtil.getCurrentUserRole();

        if (role != Role.SUPER_ADMIN) {
            throw new RuntimeException("Only SuperAdmin can review proposals");
        }

        Proposal proposal = proposalRepository.findById(proposalId).orElseThrow();
        SuperAdmin superAdmin = superAdminRepository.findById(superAdminId).orElseThrow();

        proposal.setStatus(reviewDTO.getStatus());
        proposal.setRemarks(reviewDTO.getRemarks());
        proposal.setReviewedBy(superAdmin);
        proposal.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(proposalRepository.save(proposal));
    }

    /* ================= MAPPER ================= */
    private ProposalResponseDTO mapToDTO(Proposal proposal) {

        ProposalResponseDTO dto = new ProposalResponseDTO();
        dto.setId(proposal.getId());
        dto.setTitle(proposal.getTitle());
        dto.setDescription(proposal.getDescription());
        dto.setProposalType(proposal.getProposalType().name());
        dto.setStatus(proposal.getStatus());
        dto.setRemarks(proposal.getRemarks());
        dto.setCreatedAt(proposal.getCreatedAt());
        dto.setUpdatedAt(proposal.getUpdatedAt());

        if (proposal.getAdmin() != null) {
            dto.setCreatedByRole(Role.ADMIN);
            dto.setCreatedById(proposal.getAdmin().getAdminId());
        } else if (proposal.getSubAdmin() != null) {
            dto.setCreatedByRole(Role.SUB_ADMIN);
            dto.setCreatedById(proposal.getSubAdmin().getId());
        }

        return dto;
    }
}






/*
package com.tutor.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tutor.config.SecurityUtil;
import com.tutor.dto.ProposalRequestDTO;
import com.tutor.dto.ProposalResponseDTO;
import com.tutor.dto.ProposalReviewDTO;
import com.tutor.entity.Admin;
import com.tutor.entity.Proposal;
import com.tutor.entity.SubAdmin;
import com.tutor.entity.SuperAdmin;
import com.tutor.enums.Role;
import com.tutor.repository.AdminRepository;
import com.tutor.repository.ProposalRepository;
import com.tutor.repository.SubAdminRepository;
import com.tutor.repository.SuperAdminRepository;
import com.tutor.enums.ProposalType;
import com.tutor.enums.Status;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ProposalServiceImpl implements ProposalService {

	@Autowired
    private  ProposalRepository proposalRepository;
	@Autowired
    private  AdminRepository adminRepository;
	@Autowired
    private  SubAdminRepository subAdminRepository;
	@Autowired
    private  SuperAdminRepository superAdminRepository;

    // ================= CREATE PROPOSAL ================= 

    @Override
    public ProposalResponseDTO createProposal(ProposalRequestDTO requestDTO) {

        Long userId = SecurityUtil.getCurrentUserId();
        Role role = SecurityUtil.getCurrentUserRole();

        Proposal proposal = new Proposal();
        proposal.setTitle(requestDTO.getTitle());
        proposal.setDescription(requestDTO.getDescription());
//        proposal.setProposalType(requestDTO.getProposalType());
//        proposal.setStatus(ProposalStatus.PENDING);
        proposal.setProposalType(ProposalType.valueOf(requestDTO.getProposalType()));
        proposal.setStatus(Status.PENDING);

        proposal.setCreatedAt(LocalDateTime.now());

        if (role == Role.ADMIN) {
            Admin admin = adminRepository.findById(userId).orElseThrow();
            proposal.setAdmin(admin);
        } else if (role == Role.SUB_ADMIN) {
            SubAdmin subAdmin = subAdminRepository.findById(userId).orElseThrow();
            proposal.setSubAdmin(subAdmin);
        } else {
            throw new RuntimeException("Only Admin or SubAdmin can create proposals");
        }

        return mapToDTO(proposalRepository.save(proposal));
    }

    //================= VIEW OWN PROPOSALS ================= 

    @Override
    public List<ProposalResponseDTO> getMyProposals() {

        Long userId = SecurityUtil.getCurrentUserId();
        Role role = SecurityUtil.getCurrentUserRole();

        List<Proposal> proposals;

        if (role == Role.ADMIN) {
            proposals = proposalRepository.findByAdminId(userId);
        } else if (role == Role.SUB_ADMIN) {
            proposals = proposalRepository.findBySubAdminId(userId);
        } else {
            throw new RuntimeException("Invalid role");
        }

        return proposals.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // ================= SUPERADMIN VIEW ================= 

    @Override
    public List<ProposalResponseDTO> getPendingProposals() {

        return proposalRepository
                .findByStatus(Status.PENDING)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

 // REVIEW PROPOSal

    @Override
    public ProposalResponseDTO reviewProposal(Long proposalId, ProposalReviewDTO reviewDTO) {

        Long superAdminId = SecurityUtil.getCurrentUserId();
        Role role = SecurityUtil.getCurrentUserRole();

        if (role != Role.SUPER_ADMIN) {
            throw new RuntimeException("Only SuperAdmin can review proposals");
        }

        Proposal proposal = proposalRepository.findById(proposalId).orElseThrow();
        SuperAdmin superAdmin = superAdminRepository.findById(superAdminId).orElseThrow();

        proposal.setStatus(reviewDTO.getStatus());
        proposal.setRemarks(reviewDTO.getRemarks());
        proposal.setReviewedBy(superAdmin);
        proposal.setUpdatedAt(LocalDateTime.now());

        return mapToDTO(proposalRepository.save(proposal));
    }

    // ================= MAPPER ================= 

    private ProposalResponseDTO mapToDTO(Proposal proposal) {

        ProposalResponseDTO dto = new ProposalResponseDTO();
        dto.setId(proposal.getId());
        dto.setTitle(proposal.getTitle());
        dto.setDescription(proposal.getDescription());
        dto.setProposalType(proposal.getProposalType().name());
        dto.setStatus(proposal.getStatus());
        dto.setRemarks(proposal.getRemarks());
        dto.setCreatedAt(proposal.getCreatedAt());
        dto.setUpdatedAt(proposal.getUpdatedAt());

        if (proposal.getAdmin() != null) {
            dto.setCreatedByRole(Role.ADMIN);
            dto.setCreatedById(proposal.getAdmin().getAdminId());
        } else if (proposal.getSubAdmin() != null) {
            dto.setCreatedByRole(Role.SUB_ADMIN);
            dto.setCreatedById(proposal.getSubAdmin().getId());
        }

        return dto;
    }
} */
