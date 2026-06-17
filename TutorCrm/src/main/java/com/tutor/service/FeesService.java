package com.tutor.service;

import java.util.List;

import com.tutor.dto.FeesRequestDTO;
import com.tutor.dto.FeesResponseDTO;

public interface FeesService {

    FeesResponseDTO saveFees(FeesRequestDTO dto);

    List<FeesResponseDTO> getAllFees();

    FeesResponseDTO getFeesById(Long id);

    FeesResponseDTO updateFees(Long id, FeesRequestDTO dto);

    void deleteFees(Long id);
}