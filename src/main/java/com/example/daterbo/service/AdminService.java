// agas-tya21/daterbo/daterbo-5d63aa6fe5be4c5f0f3509284e781c11677bf0a1/src/main/java/com/example/daterbo/service/AdminService.java
package com.example.daterbo.service;

import com.example.daterbo.model.Admin;
import com.example.daterbo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
    
    public Optional<Admin> login(String email, String password) {
        return adminRepository.findById(email)
                .filter(admin -> passwordEncoder.matches(password, admin.getPassword()));
    }

    public Admin createAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return adminRepository.save(admin);
    }

    public Optional<Admin> updateAdmin(String email, Admin adminDetails) {
        return adminRepository.findById(email)
                .map(admin -> {
                    admin.setNamaadmin(adminDetails.getNamaadmin());
                    if (adminDetails.getPassword() != null && !adminDetails.getPassword().isEmpty()) {
                        admin.setPassword(passwordEncoder.encode(adminDetails.getPassword()));
                    }
                    return adminRepository.save(admin);
                });
    }

    public void deleteAdmin(String email) {
        adminRepository.deleteById(email);
    }
}