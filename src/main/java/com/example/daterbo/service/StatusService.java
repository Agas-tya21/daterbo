package com.example.daterbo.service;

import com.example.daterbo.model.Status;
import com.example.daterbo.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public List<Status> getAllStatus() {
        return statusRepository.findAll();
    }

    public Optional<Status> getStatusById(String id) {
        return statusRepository.findById(id);
    }

    public Status createStatus(Status status) {
        return statusRepository.save(status);
    }

    public Optional<Status> updateStatus(String id, Status statusDetails) {
        return statusRepository.findById(id)
                .map(status -> {
                    status.setNamastatus(statusDetails.getNamastatus());
                    return statusRepository.save(status);
                });
    }

    public void deleteStatus(String id) {
        statusRepository.deleteById(id);
    }
}