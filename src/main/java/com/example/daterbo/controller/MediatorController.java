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

import com.example.daterbo.model.Mediator;
import com.example.daterbo.service.MediatorService;

@RestController
@RequestMapping("/api/mediator")
public class MediatorController {

    @Autowired
    private MediatorService mediatorService;

    @GetMapping
    public ResponseEntity<List<Mediator>> getAllMediators() {
        return new ResponseEntity<>(mediatorService.getAllMediators(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mediator> getMediatorById(@PathVariable String id) {
        return mediatorService.getMediatorById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Mediator> createMediator(@RequestBody Mediator mediator) {
        return new ResponseEntity<>(mediatorService.createMediator(mediator), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mediator> updateMediator(@PathVariable String id, @RequestBody Mediator mediatorDetails) {
        return mediatorService.updateMediator(id, mediatorDetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteMediator(@PathVariable String id) {
        try {
            mediatorService.deleteMediator(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}