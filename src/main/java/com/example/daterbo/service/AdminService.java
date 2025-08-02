package com.example.daterbo.service;

import com.example.daterbo.model.Admin;
import com.example.daterbo.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Memvalidasi login admin berdasarkan email dan password.
     * @param email Email admin.
     * @param password Password admin.
     * @return Optional berisi objek Admin jika login berhasil.
     */
    public Optional<Admin> login(String email, String password) {
        // PERHATIAN: Di aplikasi nyata, gunakan hashing (misalnya, BCrypt) untuk memverifikasi password.
        return adminRepository.findById(email)
                .filter(admin -> admin.getPassword().equals(password));
    }

    /**
     * Membuat admin baru.
     * @param admin Objek admin yang akan dibuat.
     * @return Admin yang telah disimpan.
     */
    public Admin createAdmin(Admin admin) {
        // PERHATIAN: Enkripsi password sebelum menyimpannya ke database.
        return adminRepository.save(admin);
    }

    /**
     * Memperbarui password admin.
     * @param email Email admin yang akan diupdate.
     * @param adminDetails Objek yang berisi password baru.
     * @return Optional berisi admin yang telah diupdate.
     */
    public Optional<Admin> updateAdmin(String email, Admin adminDetails) {
        return adminRepository.findById(email)
                .map(admin -> {
                    admin.setPassword(adminDetails.getPassword()); // Enkripsi password baru di sini
                    return adminRepository.save(admin);
                });
    }

    /**
     * Menghapus admin berdasarkan email.
     * @param email Email admin yang akan dihapus.
     */
    public void deleteAdmin(String email) {
        adminRepository.deleteById(email);
    }
}