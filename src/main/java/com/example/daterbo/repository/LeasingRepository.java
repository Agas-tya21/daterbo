package com.example.daterbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.daterbo.model.Leasing;

@Repository
public interface LeasingRepository extends JpaRepository<Leasing, String> {
    // Metode kustom dapat ditambahkan di sini jika diperlukan
}