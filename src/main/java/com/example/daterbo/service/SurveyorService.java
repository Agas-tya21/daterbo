package com.example.daterbo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daterbo.model.Surveyor;
import com.example.daterbo.repository.SurveyorRepository;

@Service
public class SurveyorService {

    @Autowired
    private SurveyorRepository surveyorRepository;

    public List<Surveyor> getAllSurveyors() {
        return surveyorRepository.findAll();
    }

    public Optional<Surveyor> getSurveyorById(String id) {
        return surveyorRepository.findById(id);
    }

    public Surveyor createSurveyor(Surveyor surveyor) {
        return surveyorRepository.save(surveyor);
    }

    public Optional<Surveyor> updateSurveyor(String id, Surveyor surveyorDetails) {
        return surveyorRepository.findById(id)
                .map(surveyor -> {
                    surveyor.setNamasurveyor(surveyorDetails.getNamasurveyor());
                    surveyor.setNowa(surveyorDetails.getNowa());
                    surveyor.setNamaleasing(surveyorDetails.getNamaleasing());
                    surveyor.setAsalleasing(surveyorDetails.getAsalleasing());
                    return surveyorRepository.save(surveyor);
                });
    }

    public void deleteSurveyor(String id) {
        surveyorRepository.deleteById(id);
    }
}