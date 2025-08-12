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

import com.example.daterbo.model.Surveyor;
import com.example.daterbo.service.SurveyorService;

@RestController
@RequestMapping("/api/surveyor")
public class SurveyorController {

    @Autowired
    private SurveyorService surveyorService;

    @GetMapping
    public ResponseEntity<List<Surveyor>> getAllSurveyors() {
        return new ResponseEntity<>(surveyorService.getAllSurveyors(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Surveyor> createSurveyor(@RequestBody Surveyor surveyor) {
        return new ResponseEntity<>(surveyorService.createSurveyor(surveyor), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Surveyor> updateSurveyor(@PathVariable String id, @RequestBody Surveyor surveyorDetails) {
        return surveyorService.updateSurveyor(id, surveyorDetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSurveyor(@PathVariable String id) {
        try {
            surveyorService.deleteSurveyor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}