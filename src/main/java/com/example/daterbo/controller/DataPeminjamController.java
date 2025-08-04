package com.example.daterbo.controller;

import com.example.daterbo.model.DataPeminjam;
import com.example.daterbo.service.DataPeminjamService;
import com.example.daterbo.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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

    private final ObjectMapper objectMapper;

    public DataPeminjamController() {
        this.objectMapper = new ObjectMapper();
        // Register module untuk handle tipe data LocalDate
        this.objectMapper.registerModule(new JavaTimeModule());
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
            DataPeminjam dataPeminjam = objectMapper.readValue(dataPeminjamJson, DataPeminjam.class);

            if (fotoktp != null && !fotoktp.isEmpty()) dataPeminjam.setFotoktp(fileStorageService.storeFile(fotoktp));
            if (fotobpkb != null && !fotobpkb.isEmpty()) dataPeminjam.setFotobpkb(fileStorageService.storeFile(fotobpkb));
            if (fotostnk != null && !fotostnk.isEmpty()) dataPeminjam.setFotostnk(fileStorageService.storeFile(fotostnk));
            if (fotokk != null && !fotokk.isEmpty()) dataPeminjam.setFotokk(fileStorageService.storeFile(fotokk));
            if (fotorekeningkoran != null && !fotorekeningkoran.isEmpty()) dataPeminjam.setFotorekeningkoran(fileStorageService.storeFile(fotorekeningkoran));
            if (fotorekeninglistrik != null && !fotorekeninglistrik.isEmpty()) dataPeminjam.setFotorekeninglistrik(fileStorageService.storeFile(fotorekeninglistrik));
            if (fotobukunikah != null && !fotobukunikah.isEmpty()) dataPeminjam.setFotobukunikah(fileStorageService.storeFile(fotobukunikah));
            if (fotosertifikat != null && !fotosertifikat.isEmpty()) dataPeminjam.setFotosertifikat(fileStorageService.storeFile(fotosertifikat));

            DataPeminjam createdData = dataPeminjamService.createDataPeminjam(dataPeminjam);
            buildFileUrls(createdData);
            return new ResponseEntity<>(createdData, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
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
            if (!dataPeminjamService.getDataPeminjamByNik(nik).isPresent()) {
                return ResponseEntity.notFound().build();
            }

            DataPeminjam dataPeminjamDetails = objectMapper.readValue(dataPeminjamJson, DataPeminjam.class);

            if (fotoktp != null && !fotoktp.isEmpty()) dataPeminjamDetails.setFotoktp(fileStorageService.storeFile(fotoktp));
            if (fotobpkb != null && !fotobpkb.isEmpty()) dataPeminjamDetails.setFotobpkb(fileStorageService.storeFile(fotobpkb));
            if (fotostnk != null && !fotostnk.isEmpty()) dataPeminjamDetails.setFotostnk(fileStorageService.storeFile(fotostnk));
            if (fotokk != null && !fotokk.isEmpty()) dataPeminjamDetails.setFotokk(fileStorageService.storeFile(fotokk));
            if (fotorekeningkoran != null && !fotorekeningkoran.isEmpty()) dataPeminjamDetails.setFotorekeningkoran(fileStorageService.storeFile(fotorekeningkoran));
            if (fotorekeninglistrik != null && !fotorekeninglistrik.isEmpty()) dataPeminjamDetails.setFotorekeninglistrik(fileStorageService.storeFile(fotorekeninglistrik));
            if (fotobukunikah != null && !fotobukunikah.isEmpty()) dataPeminjamDetails.setFotobukunikah(fileStorageService.storeFile(fotobukunikah));
            if (fotosertifikat != null && !fotosertifikat.isEmpty()) dataPeminjamDetails.setFotosertifikat(fileStorageService.storeFile(fotosertifikat));

            DataPeminjam updatedData = dataPeminjamService.updateDataPeminjam(nik, dataPeminjamDetails);
            buildFileUrls(updatedData);
            return ResponseEntity.ok(updatedData);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{nik}")
    public ResponseEntity<HttpStatus> deleteDataPeminjam(@PathVariable String nik) {
        try {
            dataPeminjamService.deleteDataPeminjam(nik);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    private void buildFileUrls(DataPeminjam dataPeminjam) {
        if (dataPeminjam == null) return;
        dataPeminjam.setFotoktp(createFileUrl(dataPeminjam.getFotoktp()));
        dataPeminjam.setFotobpkb(createFileUrl(dataPeminjam.getFotobpkb()));
        dataPeminjam.setFotostnk(createFileUrl(dataPeminjam.getFotostnk()));
        dataPeminjam.setFotokk(createFileUrl(dataPeminjam.getFotokk()));
        dataPeminjam.setFotorekeningkoran(createFileUrl(dataPeminjam.getFotorekeningkoran()));
        dataPeminjam.setFotorekeninglistrik(createFileUrl(dataPeminjam.getFotorekeninglistrik()));
        dataPeminjam.setFotobukunikah(createFileUrl(dataPeminjam.getFotobukunikah()));
        dataPeminjam.setFotosertifikat(createFileUrl(dataPeminjam.getFotosertifikat()));
    }
    
    private String createFileUrl(String fileName) {
        if (fileName == null || fileName.isEmpty() || fileName.startsWith("http")) {
            return fileName;
        }
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
    }
}