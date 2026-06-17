package com.tutor.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.tutor.entity.SubAdmin;

import java.util.List;

public interface SubAdminService {

    SubAdmin createSubAdmin(SubAdmin subAdmin, Long superAdminId);

    List<SubAdmin> getAllSubAdmins(HttpServletRequest http);

    SubAdmin getSubAdminById(Long id,Long superAdminId);

    SubAdmin updateSubAdmin(Long id, SubAdmin subAdminDetails,Long superAdminId);

    void deleteSubAdmin(Long id,Long superAdminId);

  //  ResponseEntity<List<?>>  getAllManagementAdmins(HttpServletRequest http);
    ResponseEntity<?> uploadProfilePicture(Long subAdminId, MultipartFile file, HttpServletRequest request);
   }
