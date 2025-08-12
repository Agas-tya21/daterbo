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

import com.example.daterbo.model.Pic;
import com.example.daterbo.service.PicService;

@RestController
@RequestMapping("/api/pic")
public class PicController {

    @Autowired
    private PicService picService;

    @GetMapping
    public ResponseEntity<List<Pic>> getAllPics() {
        return new ResponseEntity<>(picService.getAllPics(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pic> getPicById(@PathVariable String id) {
        return picService.getPicById(id)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Pic> createPic(@RequestBody Pic pic) {
        return new ResponseEntity<>(picService.createPic(pic), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pic> updatePic(@PathVariable String id, @RequestBody Pic picDetails) {
        return picService.updatePic(id, picDetails)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletePic(@PathVariable String id) {
        try {
            picService.deletePic(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}