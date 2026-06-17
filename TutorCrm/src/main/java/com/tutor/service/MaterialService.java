package com.tutor.service;

import com.tutor.dto.MaterialRequestDTO;
import com.tutor.dto.MaterialResponseDTO;
import java.util.List;

public interface MaterialService {

    MaterialResponseDTO createMaterial(MaterialRequestDTO materialRequestDTO);
    List<MaterialResponseDTO> getAllMaterials();
    MaterialResponseDTO getMaterialById(Long id);
    MaterialResponseDTO updateMaterial(Long id, MaterialRequestDTO materialRequestDTO);
    void deleteMaterial(Long id);
}
