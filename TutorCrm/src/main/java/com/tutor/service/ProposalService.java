package com.tutor.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tutor.dto.ProposalRequestDTO;
import com.tutor.dto.ProposalResponseDTO;
import com.tutor.dto.ProposalReviewDTO;
import com.tutor.enums.Role;
@Service
public interface ProposalService {

    // Admin / SubAdmin
    ProposalResponseDTO createProposal(ProposalRequestDTO requestDTO);

//    List<ProposalResponseDTO> getMyProposals();
    List<ProposalResponseDTO> getMyProposals(Long userId, Role role); // optional params for testing


    // SuperAdmin
    List<ProposalResponseDTO> getPendingProposals();

    ProposalResponseDTO reviewProposal(Long proposalId, ProposalReviewDTO reviewDTO);
}
