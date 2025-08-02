package com.example.daterbo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daterbo.model.DataPeminjam;
import com.example.daterbo.repository.DataPeminjamRepository;

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
        return dataPeminjamRepository.save(dataPeminjam);
    }

    public Optional<DataPeminjam> updateDataPeminjam(String nik, DataPeminjam dataPeminjamDetails) {
        return dataPeminjamRepository.findById(nik)
                .map(dataPeminjam -> {
                    // Update semua field kecuali relasi, yang harus di-handle secara terpisah jika diperlukan
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
                    
                    // Update nama file jika ada yang baru (null-safe)
                    if (dataPeminjamDetails.getFotoktp() != null) dataPeminjam.setFotoktp(dataPeminjamDetails.getFotoktp());
                    if (dataPeminjamDetails.getFotobpkb() != null) dataPeminjam.setFotobpkb(dataPeminjamDetails.getFotobpkb());
                    if (dataPeminjamDetails.getFotostnk() != null) dataPeminjam.setFotostnk(dataPeminjamDetails.getFotostnk());
                    if (dataPeminjamDetails.getFotokk() != null) dataPeminjam.setFotokk(dataPeminjamDetails.getFotokk());
                    if (dataPeminjamDetails.getFotorekeningkoran() != null) dataPeminjam.setFotorekeningkoran(dataPeminjamDetails.getFotorekeningkoran());
                    if (dataPeminjamDetails.getFotorekeninglistrik() != null) dataPeminjam.setFotorekeninglistrik(dataPeminjamDetails.getFotorekeninglistrik());
                    if (dataPeminjamDetails.getFotobukunikah() != null) dataPeminjam.setFotobukunikah(dataPeminjamDetails.getFotobukunikah());
                    if (dataPeminjamDetails.getFotosertifikat() != null) dataPeminjam.setFotosertifikat(dataPeminjamDetails.getFotosertifikat());
                    
                    return dataPeminjamRepository.save(dataPeminjam);
                });
    }

    public void deleteDataPeminjam(String nik) {
        dataPeminjamRepository.deleteById(nik);
    }
}