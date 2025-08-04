// agas-tya21/daterbo/daterbo-5d63aa6fe5be4c5f0f3509284e781c11677bf0a1/src/main/java/com/example/daterbo/controller/AdminController.java
package com.example.daterbo.controller;

import com.example.daterbo.model.Admin;
import com.example.daterbo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        admins.forEach(admin -> admin.setPassword(null)); // Sembunyikan password
        return new ResponseEntity<>(admins, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Admin loginRequest) {
        Optional<Admin> adminOptional = adminService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (adminOptional.isPresent()) {
            return ResponseEntity.ok(Map.of("message", "Login berhasil"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Email atau password salah."));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        try {
            Admin newAdmin = adminService.createAdmin(admin);
            newAdmin.setPassword(null);
            return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{email}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable String email, @RequestBody Admin adminDetails) {
        Optional<Admin> updatedAdminOpt = adminService.updateAdmin(email, adminDetails);
        if (updatedAdminOpt.isPresent()) {
            Admin updatedAdmin = updatedAdminOpt.get();
            updatedAdmin.setPassword(null);
            return ResponseEntity.ok(updatedAdmin);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<HttpStatus> deleteAdmin(@PathVariable String email) {
        try {
            adminService.deleteAdmin(email);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}