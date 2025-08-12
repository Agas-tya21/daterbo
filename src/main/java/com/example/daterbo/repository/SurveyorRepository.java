package com.example.daterbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.daterbo.model.Surveyor;

@Repository
public interface SurveyorRepository extends JpaRepository<Surveyor, String> {
}