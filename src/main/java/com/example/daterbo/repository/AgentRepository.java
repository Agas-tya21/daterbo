package com.example.daterbo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.daterbo.model.Agent;

@Repository
public interface AgentRepository extends JpaRepository<Agent, String> {
}