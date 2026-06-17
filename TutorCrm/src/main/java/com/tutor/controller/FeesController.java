package com.tutor.controller;

import com.tutor.dto.FeesRequestDTO;
import com.tutor.dto.FeesResponseDTO;
import com.tutor.service.FeesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fees")
public class FeesController {

    @Autowired
    private FeesService feesService;

    // ✅ CREATE
    @PostMapping("/create")
    public FeesResponseDTO create(@RequestBody FeesRequestDTO dto) {
        return feesService.saveFees(dto);
    }

    // ✅ GET ALL
    @GetMapping("/all")
    public List<FeesResponseDTO> getAll() {
        return feesService.getAllFees();
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public FeesResponseDTO getById(@PathVariable Long id) {
        return feesService.getFeesById(id);
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public FeesResponseDTO update(
            @PathVariable Long id,
            @RequestBody FeesRequestDTO dto) {
        return feesService.updateFees(id, dto);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        feesService.deleteFees(id);
        return "Fees deleted successfully";
    }
}
