package com.tutor.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tutor.entity.SuperAdmin;
import com.tutor.service.SuperAdminService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService service;

    @PostMapping("/create")
    public ResponseEntity<?> createSuperAdmin( @RequestBody SuperAdmin superAdmin) {
        System.out.println(" Controller Calling ...");
        SuperAdmin created = service.create(superAdmin);
        return ResponseEntity.ok().body(created);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SuperAdmin user, HttpServletResponse response){
        return service.login(user, response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAll(HttpServletRequest http) {
        List<SuperAdmin> list = service.getAll(http);
        return ResponseEntity.ok().body(list);
    }


    @GetMapping
    public ResponseEntity<?> getLoginSuperAdmin(HttpServletRequest req) {
        ResponseEntity<?> found = service.getLoginSuperAdmin(req);
        return ResponseEntity.ok().body( found );
    }


    @GetMapping("/count-admins")
    public ResponseEntity<?> countAdmins() {
        ResponseEntity<?> saved = service.countAdmin();
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/count-subAdmins")
    public ResponseEntity<?> countSubAdmins() {
        ResponseEntity<?> saved = service.countSubAdmin();
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @RequestBody SuperAdmin request,
            HttpServletRequest http) {
        SuperAdmin updated = service.update(id, request, http);
        return ResponseEntity.ok(updated);

    }

    // DELETE SUPERADMIN
    @DeleteMapping("/delete/{superAdminId}")
    public ResponseEntity<String> delete(@PathVariable Long superAdminId, HttpServletRequest http) {
        service.delete(superAdminId, http);
        return ResponseEntity.ok("SuperAdmin deleted successfully");
    }

  @GetMapping("/admin/getAll")
  public ResponseEntity<List<?>> getAllAdmins(HttpServletRequest http){
      return service.getAllAdmins(http);
  }

  @PostMapping("/upload-profile/{superAdminId}")
  public ResponseEntity<?> uploadProfile(
          @PathVariable Long superAdminId,
          @RequestParam("file") MultipartFile file,
          HttpServletRequest request
  ) {
      return service.uploadProfilePicture(superAdminId, file, request);
  }

}
