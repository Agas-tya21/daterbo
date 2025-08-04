package com.example.daterbo.service;

import com.example.daterbo.model.DataPeminjam;
import com.example.daterbo.model.Status;
import com.example.daterbo.repository.DataPeminjamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class DataPeminjamService {

    @Autowired
    private DataPeminjamRepository dataPeminjamRepository;

    public List<DataPeminjam> getAllDataPeminjam() {
        return dataPeminjamRepository.findAll();
    }

    public Optional<DataPeminjam> getDataPeminjamByNik(String nik) {
        return dataPeminjamRepository.findById(nik);
    }

    public DataPeminjam createDataPeminjam(DataPeminjam dataPeminjam) {
        if (dataPeminjam.getTglinput() == null) {
            dataPeminjam.setTglinput(LocalDate.now());
        }
        if (dataPeminjam.getStatus() == null) {
            Status defaultStatus = new Status();
            defaultStatus.setIdstatus("S001");
            dataPeminjam.setStatus(defaultStatus);
        }
        return dataPeminjamRepository.save(dataPeminjam);
    }

    public DataPeminjam updateDataPeminjam(String nik, DataPeminjam dataPeminjamDetails) {
        DataPeminjam dataPeminjam = dataPeminjamRepository.findById(nik)
                .orElseThrow(() -> new RuntimeException("Data Peminjam not found with nik: " + nik));

        // Update fields dari data JSON
        dataPeminjam.setUser(dataPeminjamDetails.getUser());
        dataPeminjam.setStatus(dataPeminjamDetails.getStatus());
        dataPeminjam.setLeasing(dataPeminjamDetails.getLeasing());
        dataPeminjam.setTglinput(dataPeminjamDetails.getTglinput());
        dataPeminjam.setTglpenerimaan(dataPeminjamDetails.getTglpenerimaan());
        dataPeminjam.setTglpencairan(dataPeminjamDetails.getTglpencairan());
        dataPeminjam.setNamapeminjam(dataPeminjamDetails.getNamapeminjam());
        dataPeminjam.setNohp(dataPeminjamDetails.getNohp());
        dataPeminjam.setAset(dataPeminjamDetails.getAset());
        dataPeminjam.setTahunaset(dataPeminjamDetails.getTahunaset());
        dataPeminjam.setAlamat(dataPeminjamDetails.getAlamat());
        dataPeminjam.setKota(dataPeminjamDetails.getKota());
        dataPeminjam.setKecamatan(dataPeminjamDetails.getKecamatan());
        dataPeminjam.setKeterangan(dataPeminjamDetails.getKeterangan());
        
        // Update nama file hanya jika ada file baru yang diunggah
        if (dataPeminjamDetails.getFotoktp() != null) dataPeminjam.setFotoktp(dataPeminjamDetails.getFotoktp());
        if (dataPeminjamDetails.getFotobpkb() != null) dataPeminjam.setFotobpkb(dataPeminjamDetails.getFotobpkb());
        if (dataPeminjamDetails.getFotostnk() != null) dataPeminjam.setFotostnk(dataPeminjamDetails.getFotostnk());
        if (dataPeminjamDetails.getFotokk() != null) dataPeminjam.setFotokk(dataPeminjamDetails.getFotokk());
        if (dataPeminjamDetails.getFotorekeningkoran() != null) dataPeminjam.setFotorekeningkoran(dataPeminjamDetails.getFotorekeningkoran());
        if (dataPeminjamDetails.getFotorekeninglistrik() != null) dataPeminjam.setFotorekeninglistrik(dataPeminjamDetails.getFotorekeninglistrik());
        if (dataPeminjamDetails.getFotobukunikah() != null) dataPeminjam.setFotobukunikah(dataPeminjamDetails.getFotobukunikah());
        if (dataPeminjamDetails.getFotosertifikat() != null) dataPeminjam.setFotosertifikat(dataPeminjamDetails.getFotosertifikat());
                    
        return dataPeminjamRepository.save(dataPeminjam);
    }

    public void deleteDataPeminjam(String nik) {
        if (!dataPeminjamRepository.existsById(nik)) {
            throw new RuntimeException("Data Peminjam not found with nik: " + nik);
        }
        dataPeminjamRepository.deleteById(nik);
    }
}