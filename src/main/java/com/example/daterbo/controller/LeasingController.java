package com.example.daterbo.controller;

import com.example.daterbo.model.Leasing;
import com.example.daterbo.service.LeasingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/leasing")
public class LeasingController {

    @Autowired
    private LeasingService leasingService;

    @GetMapping
    public ResponseEntity<List<Leasing>> getAllLeasing() {
        List<Leasing> listLeasing = leasingService.getAllLeasing();
        return new ResponseEntity<>(listLeasing, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Leasing> getLeasingById(@PathVariable String id) {
        Optional<Leasing> leasing = leasingService.getLeasingById(id);
        return leasing.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Leasing> createLeasing(@RequestBody Leasing leasing) {
        Leasing newLeasing = leasingService.createLeasing(leasing);
        return new ResponseEntity<>(newLeasing, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Leasing> updateLeasing(@PathVariable String id, @RequestBody Leasing leasingDetails) {
        Optional<Leasing> updatedLeasing = leasingService.updateLeasing(id, leasingDetails);
        return updatedLeasing.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteLeasing(@PathVariable String id) {
        try {
            leasingService.deleteLeasing(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}