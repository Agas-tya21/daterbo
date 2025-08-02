package com.example.daterbo.service;

import com.example.daterbo.model.Leasing;
import com.example.daterbo.repository.LeasingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class LeasingService {

    @Autowired
    private LeasingRepository leasingRepository;

    public List<Leasing> getAllLeasing() {
        return leasingRepository.findAll();
    }

    public Optional<Leasing> getLeasingById(String id) {
        return leasingRepository.findById(id);
    }

    public Leasing createLeasing(Leasing leasing) {
        return leasingRepository.save(leasing);
    }

    public Optional<Leasing> updateLeasing(String id, Leasing leasingDetails) {
        return leasingRepository.findById(id)
                .map(leasing -> {
                    leasing.setNamaleasing(leasingDetails.getNamaleasing());
                    return leasingRepository.save(leasing);
                });
    }

    public void deleteLeasing(String id) {
        leasingRepository.deleteById(id);
    }
}