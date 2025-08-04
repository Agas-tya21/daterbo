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

    public Optional<DataPeminjam> getDataPeminjamById(String id) {
        return dataPeminjamRepository.findById(id);
    }

    public DataPeminjam createDataPeminjam(DataPeminjam dataPeminjam) {
        if (dataPeminjam.getTglinput() == null) {
            dataPeminjam.setTglinput(LocalDate.now());
        }
        if (dataPeminjam.getStatus() == null) {
            Status defaultStatus = new Status();
            defaultStatus.setIdstatus("S001"); // Status default: BARU
            dataPeminjam.setStatus(defaultStatus);
        }
        return dataPeminjamRepository.save(dataPeminjam);
    }

    public DataPeminjam updateDataPeminjam(String id, DataPeminjam dataPeminjamDetails) {
        DataPeminjam dataPeminjam = dataPeminjamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data Peminjam not found with id: " + id));

        // Update fields dari data JSON
        dataPeminjam.setNik(dataPeminjamDetails.getNik());
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

    public void deleteDataPeminjam(String id) {
        if (!dataPeminjamRepository.existsById(id)) {
            throw new RuntimeException("Data Peminjam not found with id: " + id);
        }
        dataPeminjamRepository.deleteById(id);
    }

    public DataPeminjam prosesPencairan(String id) {
        DataPeminjam dataPeminjam = dataPeminjamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data Peminjam not found with id: " + id));

        Status prosesStatus = new Status();
        prosesStatus.setIdstatus("S002"); 
        dataPeminjam.setStatus(prosesStatus);
        dataPeminjam.setTglpenerimaan(LocalDate.now());

        return dataPeminjamRepository.save(dataPeminjam);
    }

    public DataPeminjam batalPeminjaman(String id) {
        DataPeminjam dataPeminjam = dataPeminjamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data Peminjam not found with id: " + id));

        Status batalStatus = new Status();
        batalStatus.setIdstatus("S004"); 
        dataPeminjam.setStatus(batalStatus);

        return dataPeminjamRepository.save(dataPeminjam);
    }

    /**
     * Mengubah status peminjaman menjadi 'CAIR' (S003).
     * @param id ID data peminjam.
     * @return Data peminjam yang telah diperbarui.
     */
    public DataPeminjam cairkanPeminjaman(String id) {
        DataPeminjam dataPeminjam = dataPeminjamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data Peminjam not found with id: " + id));

        // Set status ke "CAIR" (S003)
        Status cairStatus = new Status();
        cairStatus.setIdstatus("S003"); 
        dataPeminjam.setStatus(cairStatus);

        return dataPeminjamRepository.save(dataPeminjam);
    }
}