package com.example.daterbo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.daterbo.model.DataPeminjam;
import com.example.daterbo.service.DataPeminjamService;
import com.example.daterbo.service.FileStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

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
            @RequestPart(value = "fotosertifikat", required = false) MultipartFile fotosertifikat,
            @RequestPart(value = "fotoktppenjamin", required = false) MultipartFile fotoktppenjamin) {
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
            if (fotoktppenjamin != null && !fotoktppenjamin.isEmpty()) dataPeminjam.setFotoktppenjamin(fileStorageService.storeFile(fotoktppenjamin));

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

    @GetMapping("/{id}")
    public ResponseEntity<DataPeminjam> getDataPeminjamById(@PathVariable String id) {
        Optional<DataPeminjam> dataPeminjam = dataPeminjamService.getDataPeminjamById(id);
        dataPeminjam.ifPresent(this::buildFileUrls);
        return dataPeminjam.map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<DataPeminjam> updateDataPeminjam(
            @PathVariable String id,
            @RequestPart("data") String dataPeminjamJson,
            @RequestPart(value = "fotoktp", required = false) MultipartFile fotoktp,
            @RequestPart(value = "fotobpkb", required = false) MultipartFile fotobpkb,
            @RequestPart(value = "fotostnk", required = false) MultipartFile fotostnk,
            @RequestPart(value = "fotokk", required = false) MultipartFile fotokk,
            @RequestPart(value = "fotorekeningkoran", required = false) MultipartFile fotorekeningkoran,
            @RequestPart(value = "fotorekeninglistrik", required = false) MultipartFile fotorekeninglistrik,
            @RequestPart(value = "fotobukunikah", required = false) MultipartFile fotobukunikah,
            @RequestPart(value = "fotosertifikat", required = false) MultipartFile fotosertifikat,
            @RequestPart(value = "fotoktppenjamin", required = false) MultipartFile fotoktppenjamin) {
        try {
            DataPeminjam dataPeminjamDetails = objectMapper.readValue(dataPeminjamJson, DataPeminjam.class);

            if (fotoktp != null && !fotoktp.isEmpty()) dataPeminjamDetails.setFotoktp(fileStorageService.storeFile(fotoktp));
            if (fotobpkb != null && !fotobpkb.isEmpty()) dataPeminjamDetails.setFotobpkb(fileStorageService.storeFile(fotobpkb));
            if (fotostnk != null && !fotostnk.isEmpty()) dataPeminjamDetails.setFotostnk(fileStorageService.storeFile(fotostnk));
            if (fotokk != null && !fotokk.isEmpty()) dataPeminjamDetails.setFotokk(fileStorageService.storeFile(fotokk));
            if (fotorekeningkoran != null && !fotorekeningkoran.isEmpty()) dataPeminjamDetails.setFotorekeningkoran(fileStorageService.storeFile(fotorekeningkoran));
            if (fotorekeninglistrik != null && !fotorekeninglistrik.isEmpty()) dataPeminjamDetails.setFotorekeninglistrik(fileStorageService.storeFile(fotorekeninglistrik));
            if (fotobukunikah != null && !fotobukunikah.isEmpty()) dataPeminjamDetails.setFotobukunikah(fileStorageService.storeFile(fotobukunikah));
            if (fotosertifikat != null && !fotosertifikat.isEmpty()) dataPeminjamDetails.setFotosertifikat(fileStorageService.storeFile(fotosertifikat));
            if (fotoktppenjamin != null && !fotoktppenjamin.isEmpty()) dataPeminjamDetails.setFotoktppenjamin(fileStorageService.storeFile(fotoktppenjamin));

            DataPeminjam updatedData = dataPeminjamService.updateDataPeminjam(id, dataPeminjamDetails);
            buildFileUrls(updatedData);
            return ResponseEntity.ok(updatedData);
        } catch (IOException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteDataPeminjam(@PathVariable String id) {
        try {
            dataPeminjamService.deleteDataPeminjam(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}/proses")
    public ResponseEntity<DataPeminjam> prosesPencairan(@PathVariable String id) {
        try {
            DataPeminjam updatedData = dataPeminjamService.prosesPencairan(id);
            buildFileUrls(updatedData);
            return ResponseEntity.ok(updatedData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/batal")
    public ResponseEntity<DataPeminjam> batalPeminjaman(@PathVariable String id) {
        try {
            DataPeminjam updatedData = dataPeminjamService.batalPeminjaman(id);
            buildFileUrls(updatedData);
            return ResponseEntity.ok(updatedData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/cair")
    public ResponseEntity<DataPeminjam> cairkanPeminjaman(@PathVariable String id) {
        try {
            DataPeminjam updatedData = dataPeminjamService.cairkanPeminjaman(id);
            buildFileUrls(updatedData);
            return ResponseEntity.ok(updatedData);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
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
        dataPeminjam.setFotoktppenjamin(createFileUrl(dataPeminjam.getFotoktppenjamin()));
    }
    
    private String createFileUrl(String fileName) {
        if (fileName == null || fileName.isEmpty() || fileName.startsWith("http")) {
            return fileName;
        }
        
        // Spring Boot akan secara otomatis menggunakan skema yang benar (https)
        // karena kita sudah mengatur server.forward-headers-strategy=framework
        return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/uploads/")
                .path(fileName)
                .toUriString();
    }
}