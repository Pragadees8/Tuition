package com.tutor.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.tutor.entity.SubAdmin;
import com.tutor.service.SubAdminService;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin/subadmins")
public class SubAdminController {

    @Autowired
    private SubAdminService service;
//
//    @Autowired
//    private ManagementAdminService managementAdminService;

    // CREATE
    @PostMapping("/create/{superAdminId}")
    public SubAdmin create(@RequestBody SubAdmin subAdmin, @PathVariable Long superAdminId) {

        return service.createSubAdmin(subAdmin, superAdminId);
    }

    // GET ALL
    @GetMapping("/getAll")
    public List<SubAdmin> getAll(HttpServletRequest http) {
        return service.getAllSubAdmins(http);
    }

    // GET BY ID
    @GetMapping("/{id}/superadmin/{superAdminId}")
    public SubAdmin getById(@PathVariable Long id,@PathVariable Long superAdminId) {
        return service.getSubAdminById(id,superAdminId);
    }

    // UPDATE
    @PutMapping("/update/{id}/superadmin/{superAdminId}")
    public SubAdmin update(
            @PathVariable Long id,
            @RequestBody SubAdmin subAdminDetails,@PathVariable Long superAdminId) {
        return service.updateSubAdmin(id, subAdminDetails,superAdminId);
    }

    // DELETE
    @DeleteMapping("/delete/{id}/superadmin/{superAdminId}")
    public String delete(@PathVariable Long id,@PathVariable Long superAdminId) {
        service.deleteSubAdmin(id, superAdminId);
        return "SubAdmin deleted successfully";
    }

    @PostMapping("/upload-profile/{subAdminId}")
    public ResponseEntity<?> uploadProfile(
            @PathVariable Long subAdminId,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request
    ) {
        return service.uploadProfilePicture(subAdminId, file, request);
    }

}
