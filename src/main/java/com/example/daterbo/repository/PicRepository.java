package com.example.daterbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.daterbo.model.Pic;

@Repository
public interface PicRepository extends JpaRepository<Pic, String> {
}