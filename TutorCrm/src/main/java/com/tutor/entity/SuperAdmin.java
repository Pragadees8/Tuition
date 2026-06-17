package com.tutor.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import com.tutor.enums.Role;

@Entity
@Table(name = "super_admins")
public class SuperAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long superAdminId;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.SUPER_ADMIN;

    @Column(name = "profile_picture")
    private String profilePicture;
    
    public SuperAdmin() {}

    public SuperAdmin(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.role = Role.SUPER_ADMIN;
    }

    // ===============================
    // Getters & Setters
    // ===============================
    public Long getSuperAdminId() {
        return superAdminId;
    }

    public void setSuperAdminId(Long superAdminId) {
        this.superAdminId = superAdminId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

//    public Set<SubAdmin> getSubAdmins() {
//        return subAdmins;
//    }
//
//    public void setSubAdmins(Set<SubAdmin> subAdmins) {
//        this.subAdmins = subAdmins;
//    }
//
//    // Helper method to add SubAdmin
//    public void addSubAdmin(SubAdmin subAdmin) {
//        subAdmins.add(subAdmin);
//        subAdmin.setCreatedBy(this);
//    }
//
//    // Helper method to remove SubAdmin
//    public void removeSubAdmin(SubAdmin subAdmin) {
//        subAdmins.remove(subAdmin);
//        subAdmin.setCreatedBy(null);
//    }
}
