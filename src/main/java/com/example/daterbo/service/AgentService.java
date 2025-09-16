package com.example.daterbo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daterbo.model.Agent;
import com.example.daterbo.repository.AgentRepository;

@Service
public class AgentService {

    @Autowired
    private AgentRepository agentRepository;

    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    public Optional<Agent> getAgentById(String id) {
        return agentRepository.findById(id);
    }

    public Agent createAgent(Agent agent) {
        if (agent.getIdagent() == null || agent.getIdagent().isEmpty()) {
            agent.setIdagent(UUID.randomUUID().toString());
        }
        return agentRepository.save(agent);
    }

    public Optional<Agent> updateAgent(String id, Agent agentDetails) {
        return agentRepository.findById(id)
                .map(agent -> {
                    agent.setNamaagent(agentDetails.getNamaagent());
                    agent.setNamaleasing(agentDetails.getNamaleasing());
                    agent.setAsalleasing(agentDetails.getAsalleasing());
                    agent.setNohp(agentDetails.getNohp());
                    return agentRepository.save(agent);
                });
    }

    public void deleteAgent(String id) {
        agentRepository.deleteById(id);
    }
}