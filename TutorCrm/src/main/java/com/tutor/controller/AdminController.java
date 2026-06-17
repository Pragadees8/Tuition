package com.tutor.controller;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tutor.entity.Admin;
import com.tutor.service.AdminService;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/superadmin/admins")
public class AdminController {

    @Autowired
    private AdminService service;


  
    // CREATE ADMIN
    @PostMapping("/create/{superAdminId}")
    public ResponseEntity<?> createAdmin(
            @RequestBody Admin admin,
            @PathVariable Long superAdminId,
            HttpServletRequest http
    ) {
        ResponseEntity<?> saved = service.createAdmin(admin, superAdminId, http);
        return ResponseEntity.ok(saved);
    }
    

    @PostMapping("/upload-profile/{adminId}")
    public ResponseEntity<?> uploadProfilePicture(
            @PathVariable Long adminId,
            @RequestParam("file") MultipartFile file
    ) {
        return service.uploadProfilePicture(adminId, file);
    }

}
