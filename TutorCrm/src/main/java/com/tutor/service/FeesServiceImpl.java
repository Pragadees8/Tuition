package com.tutor.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutor.dto.FeesRequestDTO;
import com.tutor.dto.FeesResponseDTO;
import com.tutor.entity.Admin;
import com.tutor.entity.Enrollment;
import com.tutor.entity.Fees;
import com.tutor.enums.PaymentStatus;
import com.tutor.repository.AdminRepository;
import com.tutor.repository.EnrollmentRepository;
import com.tutor.repository.FeesRepository;

@Service
public class FeesServiceImpl implements FeesService {

    @Autowired
    private FeesRepository feesRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    // ================= CREATE =================
    @Override
    public FeesResponseDTO saveFees(FeesRequestDTO dto) {

        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Enrollment enrollment = enrollmentRepository.findById(dto.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        Fees fees = new Fees();
        fees.setAdmin(admin);
        fees.setEnrollment(enrollment);
        fees.setAmount(dto.getAmount());
        fees.setPaymentMode(dto.getPaymentMode());
        fees.setPaymentStatus(PaymentStatus.valueOf(dto.getPaymentStatus()));
        fees.setPaidDate(dto.getPaidDate() != null ? dto.getPaidDate() : LocalDate.now());
        fees.setPaidAt(LocalDateTime.now());

        Fees saved = feesRepository.save(fees);

        return mapToResponse(saved);
    }

    // ================= GET ALL =================
    @Override
    public List<FeesResponseDTO> getAllFees() {

        List<Fees> list = feesRepository.findAll();
        List<FeesResponseDTO> responseList = new ArrayList<>();

        for (Fees fees : list) {
            responseList.add(mapToResponse(fees));
        }

        return responseList;
    }

    // ================= GET BY ID =================
    @Override
    public FeesResponseDTO getFeesById(Long id) {

        Fees fees = feesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fees not found"));

        return mapToResponse(fees);
    }

    // ================= UPDATE =================
    @Override
    public FeesResponseDTO updateFees(Long id, FeesRequestDTO dto) {

        Fees existing = feesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Fees not found"));

        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        Enrollment enrollment = enrollmentRepository.findById(dto.getEnrollmentId())
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        existing.setAdmin(admin);
        existing.setEnrollment(enrollment);
        existing.setAmount(dto.getAmount());
        existing.setPaymentMode(dto.getPaymentMode());
        existing.setPaymentStatus(PaymentStatus.valueOf(dto.getPaymentStatus()));
        existing.setPaidDate(dto.getPaidDate());
        existing.setPaidAt(LocalDateTime.now());

        Fees updated = feesRepository.save(existing);

        return mapToResponse(updated);
    }

    // ================= DELETE =================
    @Override
    public void deleteFees(Long id) {

        if (!feesRepository.existsById(id)) {
            throw new RuntimeException("Fees not found");
        }

        feesRepository.deleteById(id);
    }

    // ================= MAPPER =================
    private FeesResponseDTO mapToResponse(Fees fees) {

        FeesResponseDTO res = new FeesResponseDTO();
        res.setId(fees.getId());
        res.setEnrollmentId(fees.getEnrollment().getId()); // ✅ IMPORTANT
        res.setAdminId(fees.getAdmin().getAdminId());           // ✅ IMPORTANT
        res.setAmount(fees.getAmount());
        res.setPaidDate(fees.getPaidDate());
        res.setPaymentMode(fees.getPaymentMode());
        res.setPaymentStatus(fees.getPaymentStatus());
        res.setPaidAt(fees.getPaidAt());

        return res;
    }
}
