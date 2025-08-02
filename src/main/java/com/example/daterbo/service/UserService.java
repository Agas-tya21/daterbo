package com.example.daterbo.service;

import com.example.daterbo.model.User;
import com.example.daterbo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Memvalidasi login pengguna berdasarkan email dan password.
     * @param email Email pengguna.
     * @param password Password pengguna.
     * @return Optional berisi objek User jika login berhasil.
     */
    public Optional<User> login(String email, String password) {
        // PERHATIAN: Di aplikasi nyata, gunakan hashing (misalnya, BCrypt) untuk memverifikasi password.
        return userRepository.findAll().stream()
                .filter(user -> user.getEmail().equals(email) && user.getPassword().equals(password))
                .findFirst();
    }
    
    /**
     * Membuat pengguna baru (registrasi).
     * @param user Objek pengguna yang akan dibuat.
     * @return Pengguna yang telah disimpan.
     */
    public User registerUser(User user) {
        // Menghasilkan ID unik untuk pengguna baru
        user.setIduser(UUID.randomUUID().toString());
        // PERHATIAN: Enkripsi password sebelum menyimpannya ke database.
        return userRepository.save(user);
    }
    
    /**
     * Mendapatkan semua data pengguna.
     * @return Daftar semua pengguna.
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Mendapatkan pengguna berdasarkan ID.
     * @param id ID pengguna.
     * @return Optional berisi objek User jika ditemukan.
     */
    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    /**
     * Memperbarui data pengguna.
     * @param id ID pengguna yang akan diupdate.
     * @param userDetails Objek yang berisi data baru.
     * @return Optional berisi pengguna yang telah diupdate.
     */
    public Optional<User> updateUser(String id, User userDetails) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setNamauser(userDetails.getNamauser());
                    user.setEmail(userDetails.getEmail());
                    // Anda bisa menambahkan logika untuk mengubah password di sini jika diperlukan
                    return userRepository.save(user);
                });
    }

    /**
     * Menghapus pengguna berdasarkan ID.
     * @param id ID pengguna yang akan dihapus.
     */
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}