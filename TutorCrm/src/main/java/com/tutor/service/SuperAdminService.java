package com.tutor.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tutor.entity.SuperAdmin;

import java.util.List;

@Service
public interface SuperAdminService {

    SuperAdmin create(SuperAdmin superAdmin);

    List<SuperAdmin> getAll(HttpServletRequest http);

    ResponseEntity<?> getLoginSuperAdmin(HttpServletRequest req);

    // SuperAdmin getByEmail(String email);

    SuperAdmin update(Long id, SuperAdmin updated, HttpServletRequest http);

    // SuperAdmin partialUpdate(Long id, SuperAdmin updates);

    void delete(Long superAdminId, HttpServletRequest http);

    ResponseEntity<?> login(SuperAdmin user, HttpServletResponse response);

    ResponseEntity countAdmin ();

    ResponseEntity<?>  countSubAdmin();

   // ResponseEntity<?> countSchoolRequest();

 //   ResponseEntity<List<?>> getAllSchools(HttpServletRequest http);

    ResponseEntity<List<?>> getAllAdmins(HttpServletRequest http);
    
    ResponseEntity<?> uploadProfilePicture(Long superAdminId, MultipartFile file, HttpServletRequest request);

}

