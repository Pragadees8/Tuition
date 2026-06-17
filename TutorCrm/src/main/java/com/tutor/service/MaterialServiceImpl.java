package com.tutor.service;

import com.tutor.dto.MaterialRequestDTO;
import com.tutor.dto.MaterialResponseDTO;
import com.tutor.entity.Material;
import com.tutor.entity.Teacher;
import com.tutor.entity.Subject;
import com.tutor.entity.Admin;
import com.tutor.enums.MaterialStatus;
import com.tutor.repository.MaterialRepository;
import com.tutor.repository.TeacherRepository;
import com.tutor.repository.SubjectRepository;
import com.tutor.repository.AdminRepository;
import com.tutor.service.MaterialService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public MaterialResponseDTO createMaterial(MaterialRequestDTO dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));
        Admin admin = adminRepository.findById(dto.getAdminId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        Material material = new Material();
        material.setTeacher(teacher);
        material.setSubject(subject);
        material.setAdmin(admin);
        material.setTitle(dto.getTitle());
        material.setDescription(dto.getDescription());
        material.setFileUrl(dto.getFileUrl());
        material.setUploadedAt(LocalDate.now());
        material.setStatus(dto.getStatus() != null ? dto.getStatus() : MaterialStatus.PENDING);

        Material saved = materialRepository.save(material);
        return mapToDTO(saved);
    }

    @Override
    public List<MaterialResponseDTO> getAllMaterials() {
        return materialRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MaterialResponseDTO getMaterialById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material not found"));
        return mapToDTO(material);
    }

    @Override
    public MaterialResponseDTO updateMaterial(Long id, MaterialRequestDTO dto) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material not found"));

        if (dto.getTeacherId() != null) {
            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Teacher not found"));
            material.setTeacher(teacher);
        }

        if (dto.getSubjectId() != null) {
            Subject subject = subjectRepository.findById(dto.getSubjectId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Subject not found"));
            material.setSubject(subject);
        }

        if (dto.getAdminId() != null) {
            Admin admin = adminRepository.findById(dto.getAdminId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
            material.setAdmin(admin);
        }

        material.setTitle(dto.getTitle() != null ? dto.getTitle() : material.getTitle());
        material.setDescription(dto.getDescription() != null ? dto.getDescription() : material.getDescription());
        material.setFileUrl(dto.getFileUrl() != null ? dto.getFileUrl() : material.getFileUrl());
        material.setStatus(dto.getStatus() != null ? dto.getStatus() : material.getStatus());

        Material updated = materialRepository.save(material);
        return mapToDTO(updated);
    }

    @Override
    public void deleteMaterial(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Material not found"));
        materialRepository.delete(material);
    }

    private MaterialResponseDTO mapToDTO(Material material) {
        MaterialResponseDTO dto = new MaterialResponseDTO();
        dto.setId(material.getId());
        dto.setTeacherId(material.getTeacher().getId());
        dto.setSubjectId(material.getSubject().getId());
        dto.setAdminId(material.getAdmin().getAdminId());
        dto.setTitle(material.getTitle());
        dto.setDescription(material.getDescription());
        dto.setFileUrl(material.getFileUrl());
        dto.setUploadedAt(material.getUploadedAt());
        dto.setStatus(material.getStatus());
        return dto;
    }
}
