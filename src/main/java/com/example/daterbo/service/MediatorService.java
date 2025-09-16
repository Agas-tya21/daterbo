package com.example.daterbo.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daterbo.model.Mediator;
import com.example.daterbo.repository.MediatorRepository;

@Service
public class MediatorService {

    @Autowired
    private MediatorRepository mediatorRepository;

    public List<Mediator> getAllMediators() {
        return mediatorRepository.findAll();
    }

    public Optional<Mediator> getMediatorById(String id) {
        return mediatorRepository.findById(id);
    }

    public Mediator createMediator(Mediator mediator) {
        if (mediator.getIdmediator() == null || mediator.getIdmediator().isEmpty()) {
            mediator.setIdmediator(UUID.randomUUID().toString());
        }
        return mediatorRepository.save(mediator);
    }

    public Optional<Mediator> updateMediator(String id, Mediator mediatorDetails) {
        return mediatorRepository.findById(id)
                .map(mediator -> {
                    mediator.setNamamediator(mediatorDetails.getNamamediator());
                    mediator.setAsalmediator(mediatorDetails.getAsalmediator());
                    mediator.setAsalleasing(mediatorDetails.getAsalleasing());
                    mediator.setNohp(mediatorDetails.getNohp());
                    return mediatorRepository.save(mediator);
                });
    }

    public void deleteMediator(String id) {
        mediatorRepository.deleteById(id);
    }
}