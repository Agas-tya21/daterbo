package com.example.daterbo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.daterbo.model.Agent;
import com.example.daterbo.service.AgentService;

@RestController
@RequestMapping("/api/agent")
public class AgentController {

    @Autowired
    private AgentService agentService;

    @GetMapping
    public ResponseEntity<List<Agent>> getAllAgents() {
        return new ResponseEntity<>(agentService.getAllAgents(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Agent> getAgentById(@PathVariable String id) {
        return agentService.getAgentById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Agent> createAgent(@RequestBody Agent agent) {
        return new ResponseEntity<>(agentService.createAgent(agent), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Agent> updateAgent(@PathVariable String id, @RequestBody Agent agentDetails) {
        return agentService.updateAgent(id, agentDetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteAgent(@PathVariable String id) {
        try {
            agentService.deleteAgent(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}