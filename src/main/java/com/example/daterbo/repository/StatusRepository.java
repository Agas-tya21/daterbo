package com.example.daterbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.daterbo.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, String> {
    // Metode kustom dapat ditambahkan di sini jika diperlukan
}