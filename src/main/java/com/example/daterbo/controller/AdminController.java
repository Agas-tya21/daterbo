package com.example.daterbo.controller;

import com.example.daterbo.model.Admin;
import com.example.daterbo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    // Endpoint untuk login
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Admin loginRequest) {
        Optional<Admin> adminOptional = adminService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (adminOptional.isPresent()) {
            return ResponseEntity.ok(Map.of("message", "Login berhasil"));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Email atau password salah."));
        }
    }

    // Endpoint untuk membuat admin baru (registrasi)
    @PostMapping("/register")
    public ResponseEntity<Admin> createAdmin(@RequestBody Admin admin) {
        try {
            Admin newAdmin = adminService.createAdmin(admin);
            return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint untuk mengubah password
    @PutMapping("/{email}")
    public ResponseEntity<Admin> updateAdmin(@PathVariable String email, @RequestBody Admin adminDetails) {
        Optional<Admin> updatedAdmin = adminService.updateAdmin(email, adminDetails);
        return updatedAdmin.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Endpoint untuk menghapus admin
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