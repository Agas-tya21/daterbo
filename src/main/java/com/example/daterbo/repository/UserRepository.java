package com.example.daterbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.daterbo.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    // Anda dapat menambahkan metode kustom di sini jika diperlukan
}