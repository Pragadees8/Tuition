package com.tutor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tutor.dto.ProposalRequestDTO;
import com.tutor.dto.ProposalResponseDTO;
import com.tutor.dto.ProposalReviewDTO;
import com.tutor.enums.Role;
import com.tutor.service.ProposalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proposals")
@RequiredArgsConstructor
public class ProposalController {
	@Autowired
    private  ProposalService proposalService;

    // ================= CREATE PROPOSAL (token or manual) =================
    @PostMapping("/create")
    public ResponseEntity<ProposalResponseDTO> createProposal(@RequestBody ProposalRequestDTO requestDTO) {
        ProposalResponseDTO response = proposalService.createProposal(requestDTO);
        return ResponseEntity.ok(response);
    }

    // ================= CREATE FOR ADMIN (manual) =================
    @PostMapping("/create/admin/{id}")
    public ResponseEntity<ProposalResponseDTO> createForAdmin(
            @PathVariable Long id,
            @RequestBody ProposalRequestDTO requestDTO) {

        requestDTO.setUserId(id);
        requestDTO.setRole(Role.ADMIN);
        ProposalResponseDTO response = proposalService.createProposal(requestDTO);
        return ResponseEntity.ok(response);
    }

    // ================= CREATE FOR SUBADMIN (manual) =================
    @PostMapping("/create/subadmin/{id}")
    public ResponseEntity<ProposalResponseDTO> createForSubAdmin(
            @PathVariable Long id,
            @RequestBody ProposalRequestDTO requestDTO) {

        requestDTO.setUserId(id);
        requestDTO.setRole(Role.SUB_ADMIN);
        ProposalResponseDTO response = proposalService.createProposal(requestDTO);
        return ResponseEntity.ok(response);
    }

    // ================= VIEW OWN PROPOSALS =================
    @GetMapping("/my")
    public ResponseEntity<List<ProposalResponseDTO>> getMyProposals(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Role role) {

        List<ProposalResponseDTO> proposals = proposalService.getMyProposals(userId, role);
        return ResponseEntity.ok(proposals);
    }

    // ================= SUPERADMIN VIEW PENDING =================
    @GetMapping("/pending")
    public ResponseEntity<List<ProposalResponseDTO>> getPendingProposals() {
        List<ProposalResponseDTO> pendingProposals = proposalService.getPendingProposals();
        return ResponseEntity.ok(pendingProposals);
    }

    // ================= REVIEW PROPOSAL =================
    @PutMapping("/review/{proposalId}")
    public ResponseEntity<ProposalResponseDTO> reviewProposal(
            @PathVariable Long proposalId,
            @RequestBody ProposalReviewDTO reviewDTO) {

        ProposalResponseDTO response = proposalService.reviewProposal(proposalId, reviewDTO);
        return ResponseEntity.ok(response);
    }
}






/*
package com.tutor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tutor.dto.ProposalRequestDTO;
import com.tutor.dto.ProposalResponseDTO;
import com.tutor.dto.ProposalReviewDTO;
import com.tutor.service.ProposalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/proposals")
@RequiredArgsConstructor
public class ProposalController {

    private final ProposalService proposalService;

    // ================= CREATE PROPOSAL =================
    @PostMapping("/create")
    public ResponseEntity<ProposalResponseDTO> createProposal(@RequestBody ProposalRequestDTO requestDTO) {
        ProposalResponseDTO response = proposalService.createProposal(requestDTO);
        return ResponseEntity.ok(response);
    }

    // ================= VIEW OWN PROPOSALS =================
    @GetMapping("/my")
    public ResponseEntity<List<ProposalResponseDTO>> getMyProposals() {
        List<ProposalResponseDTO> proposals = proposalService.getMyProposals();
        return ResponseEntity.ok(proposals);
    }

    // ================= SUPERADMIN VIEW PENDING =================
    @GetMapping("/pending")
    public ResponseEntity<List<ProposalResponseDTO>> getPendingProposals() {
        List<ProposalResponseDTO> pendingProposals = proposalService.getPendingProposals();
        return ResponseEntity.ok(pendingProposals);
    }

    // ================= REVIEW PROPOSAL =================
    @PutMapping("/review/{proposalId}")
    public ResponseEntity<ProposalResponseDTO> reviewProposal(
            @PathVariable Long proposalId,
            @RequestBody ProposalReviewDTO reviewDTO) {
        ProposalResponseDTO response = proposalService.reviewProposal(proposalId, reviewDTO);
        return ResponseEntity.ok(response);
    }
}
*/
