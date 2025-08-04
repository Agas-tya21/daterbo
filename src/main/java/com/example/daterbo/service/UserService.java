package com.example.daterbo.service;

import com.example.daterbo.model.User;
import com.example.daterbo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Logika untuk mencari user berdasarkan email. Spring Security akan menggunakan ini.
        User user = userRepository.findAll().stream()
                .filter(u -> u.getEmail().equals(email))
                .findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        // Menggunakan User dari Spring Security
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    /**
     * Membuat pengguna baru (registrasi).
     * Password akan dienkripsi secara otomatis.
     * @param user Objek pengguna yang akan dibuat.
     * @return Pengguna yang telah disimpan.
     */
    public User registerUser(User user) {
        // Menghasilkan ID unik untuk pengguna baru
        user.setIduser(UUID.randomUUID().toString());
        // Enkripsi password sebelum menyimpannya ke database
        user.setPassword(passwordEncoder.encode(user.getPassword()));
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
                    // Untuk mengubah password, sebaiknya buat endpoint terpisah yang lebih aman
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