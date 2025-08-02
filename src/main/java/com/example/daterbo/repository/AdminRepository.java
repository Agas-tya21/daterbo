package com.example.daterbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.daterbo.model.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, String> {
    // Metode kustom dapat ditambahkan di sini jika diperlukan
}