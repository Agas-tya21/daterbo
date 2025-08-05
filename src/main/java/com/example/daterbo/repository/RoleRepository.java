package com.example.daterbo.repository;

import com.example.daterbo.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    // Metode kustom dapat ditambahkan di sini jika diperlukan
}