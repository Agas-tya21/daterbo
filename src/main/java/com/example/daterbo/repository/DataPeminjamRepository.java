package com.example.daterbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.daterbo.model.DataPeminjam;

@Repository
public interface DataPeminjamRepository extends JpaRepository<DataPeminjam, String> {
    // Anda dapat menambahkan metode pencarian kustom di sini jika diperlukan
}