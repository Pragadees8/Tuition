package com.tutor.controller;

import com.tutor.dto.MaterialRequestDTO;

import com.tutor.dto.MaterialResponseDTO;
import com.tutor.service.MaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/material") 
public class MaterialController {

    @Autowired
    private MaterialService materialService;

    // ------------------- CREATE -------------------
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createMaterial(@Valid @RequestBody MaterialRequestDTO dto) {
        MaterialResponseDTO material = materialService.createMaterial(dto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Material created successfully");
        response.put("material", material);
        return response;
    }

    // ------------------- READ ALL -------------------
    @GetMapping
    public Map<String, Object> getAllMaterials() {
        List<MaterialResponseDTO> materials = materialService.getAllMaterials();
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Materials retrieved successfully");
        response.put("materials", materials);
        return response;
    }

    // ------------------- READ BY ID -------------------
    @GetMapping("/{id}")
    public Map<String, Object> getMaterialById(@PathVariable Long id) {
        MaterialResponseDTO material = materialService.getMaterialById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Material retrieved successfully");
        response.put("material", material);
        return response;
    }

    // ------------------- UPDATE -------------------
    @PutMapping("/{id}")
    public Map<String, Object> updateMaterial(@PathVariable Long id,
                                              @Valid @RequestBody MaterialRequestDTO dto) {
        MaterialResponseDTO updatedMaterial = materialService.updateMaterial(id, dto);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Material updated successfully");
        response.put("material", updatedMaterial);
        return response;
    }

    // ------------------- DELETE -------------------
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteMaterial(@PathVariable Long id) {
        materialService.deleteMaterial(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Material deleted successfully");
        return response;
    }
}
