package com.example.daterbo.controller;

import com.example.daterbo.model.DataPeminjam;
import com.example.daterbo.service.DataPeminjamService;
import com.example.daterbo.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/datapeminjam")
public class DataPeminjamController {

    @Autowired
    private DataPeminjamService dataPeminjamService;

    @Autowired
    private FileStorageService fileStorageService;

    // Helper untuk membangun URL file
    private void buildFileUrls(DataPeminjam dataPeminjam) {
        if (dataPeminjam == null) return;
        dataPeminjam.setFotoktp(buildUrl(dataPeminjam.getFotoktp()));
        dataPeminjam.setFotobpkb(buildUrl(dataPeminjam.getFotobpkb()));
        dataPeminjam.setFotostnk(buildUrl(dataPeminjam.getFotostnk()));
        dataPeminjam.setFotokk(buildUrl(dataPeminjam.getFotokk()));
        dataPeminjam.setFotorekeningkoran(buildUrl(dataPeminjam.getFotorekeningkoran()));
        dataPeminjam.setFotorekeninglistrik(buildUrl(dataPeminjam.getFotorekeninglistrik()));
        dataPeminjam.setFotobukunikah(buildUrl(dataPeminjam.getFotobukunikah()));
        dataPeminjam.setFotosertifikat(buildUrl(dataPeminjam.getFotosertifikat()));
    }
    
    private String buildUrl(String fileName) {
        if (fileName != null && !fileName.isEmpty() && !fileName.startsWith("http")) {
            return ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/uploads/")
                    .path(fileName)
                    .toUriString();
        }
        return fileName;
    }
    
    private String storeFile(MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            return fileStorageService.storeFile(file);
        }
        return null;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<DataPeminjam> createDataPeminjam(
            @RequestPart("data") String dataPeminjamJson,
            @RequestPart(value = "fotoktp", required = false) MultipartFile fotoktp,
            @RequestPart(value = "fotobpkb", required = false) MultipartFile fotobpkb,
            @RequestPart(value = "fotostnk", required = false) MultipartFile fotostnk,
            @RequestPart(value = "fotokk", required = false) MultipartFile fotokk,
            @RequestPart(value = "fotorekeningkoran", required = false) MultipartFile fotorekeningkoran,
            @RequestPart(value = "fotorekeninglistrik", required = false) MultipartFile fotorekeninglistrik,
            @RequestPart(value = "fotobukunikah", required = false) MultipartFile fotobukunikah,
            @RequestPart(value = "fotosertifikat", required = false) MultipartFile fotosertifikat) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DataPeminjam dataPeminjam = objectMapper.readValue(dataPeminjamJson, DataPeminjam.class);

            dataPeminjam.setFotoktp(storeFile(fotoktp));
            dataPeminjam.setFotobpkb(storeFile(fotobpkb));
            dataPeminjam.setFotostnk(storeFile(fotostnk));
            dataPeminjam.setFotokk(storeFile(fotokk));
            dataPeminjam.setFotorekeningkoran(storeFile(fotorekeningkoran));
            dataPeminjam.setFotorekeninglistrik(storeFile(fotorekeninglistrik));
            dataPeminjam.setFotobukunikah(storeFile(fotobukunikah));
            dataPeminjam.setFotosertifikat(storeFile(fotosertifikat));

            DataPeminjam createdData = dataPeminjamService.createDataPeminjam(dataPeminjam);
            buildFileUrls(createdData);
            return new ResponseEntity<>(createdData, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<DataPeminjam>> getAllDataPeminjam() {
        List<DataPeminjam> listData = dataPeminjamService.getAllDataPeminjam();
        listData.forEach(this::buildFileUrls);
        return new ResponseEntity<>(listData, HttpStatus.OK);
    }

    @GetMapping("/{nik}")
    public ResponseEntity<DataPeminjam> getDataPeminjamByNik(@PathVariable String nik) {
        Optional<DataPeminjam> dataPeminjam = dataPeminjamService.getDataPeminjamByNik(nik);
        dataPeminjam.ifPresent(this::buildFileUrls);
        return dataPeminjam.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{nik}", consumes = {"multipart/form-data"})
    public ResponseEntity<DataPeminjam> updateDataPeminjam(
            @PathVariable String nik,
            @RequestPart("data") String dataPeminjamJson,
            @RequestPart(value = "fotoktp", required = false) MultipartFile fotoktp,
            @RequestPart(value = "fotobpkb", required = false) MultipartFile fotobpkb,
            @RequestPart(value = "fotostnk", required = false) MultipartFile fotostnk,
            @RequestPart(value = "fotokk", required = false) MultipartFile fotokk,
            @RequestPart(value = "fotorekeningkoran", required = false) MultipartFile fotorekeningkoran,
            @RequestPart(value = "fotorekeninglistrik", required = false) MultipartFile fotorekeninglistrik,
            @RequestPart(value = "fotobukunikah", required = false) MultipartFile fotobukunikah,
            @RequestPart(value = "fotosertifikat", required = false) MultipartFile fotosertifikat) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DataPeminjam dataPeminjamDetails = objectMapper.readValue(dataPeminjamJson, DataPeminjam.class);

            dataPeminjamDetails.setFotoktp(storeFile(fotoktp));
            dataPeminjamDetails.setFotobpkb(storeFile(fotobpkb));
            dataPeminjamDetails.setFotostnk(storeFile(fotostnk));
            dataPeminjamDetails.setFotokk(storeFile(fotokk));
            dataPeminjamDetails.setFotorekeningkoran(storeFile(fotorekeningkoran));
            dataPeminjamDetails.setFotorekeninglistrik(storeFile(fotorekeninglistrik));
            dataPeminjamDetails.setFotobukunikah(storeFile(fotobukunikah));
            dataPeminjamDetails.setFotosertifikat(storeFile(fotosertifikat));

            Optional<DataPeminjam> updatedData = dataPeminjamService.updateDataPeminjam(nik, dataPeminjamDetails);
            updatedData.ifPresent(this::buildFileUrls);

            return updatedData.map(ResponseEntity::ok)
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{nik}")
    public ResponseEntity<HttpStatus> deleteDataPeminjam(@PathVariable String nik) {
        try {
            dataPeminjamService.deleteDataPeminjam(nik);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}