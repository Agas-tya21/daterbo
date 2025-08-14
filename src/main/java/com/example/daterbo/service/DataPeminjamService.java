package com.example.daterbo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.daterbo.model.DataPeminjam;
import com.example.daterbo.model.Status;
import com.example.daterbo.repository.DataPeminjamRepository;

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

        // Hanya perbarui field jika nilainya tidak null
        if (dataPeminjamDetails.getNik() != null) dataPeminjam.setNik(dataPeminjamDetails.getNik());
        if (dataPeminjamDetails.getUser() != null) dataPeminjam.setUser(dataPeminjamDetails.getUser());
        if (dataPeminjamDetails.getStatus() != null) dataPeminjam.setStatus(dataPeminjamDetails.getStatus());
        if (dataPeminjamDetails.getPic() != null) dataPeminjam.setPic(dataPeminjamDetails.getPic());
        if (dataPeminjamDetails.getSurveyor() != null) dataPeminjam.setSurveyor(dataPeminjamDetails.getSurveyor());

        // Penanganan khusus untuk Leasing
        if (dataPeminjamDetails.getLeasing() != null) {
            if (dataPeminjamDetails.getLeasing().getIdleasing() == null || dataPeminjamDetails.getLeasing().getIdleasing().isEmpty()) {
                dataPeminjam.setLeasing(null);
            } else {
                dataPeminjam.setLeasing(dataPeminjamDetails.getLeasing());
            }
        }

        if (dataPeminjamDetails.getTglinput() != null) dataPeminjam.setTglinput(dataPeminjamDetails.getTglinput());
        if (dataPeminjamDetails.getTglpenerimaan() != null) dataPeminjam.setTglpenerimaan(dataPeminjamDetails.getTglpenerimaan());
        if (dataPeminjamDetails.getTglpencairan() != null) dataPeminjam.setTglpencairan(dataPeminjamDetails.getTglpencairan());
        if (dataPeminjamDetails.getNamapeminjam() != null) dataPeminjam.setNamapeminjam(dataPeminjamDetails.getNamapeminjam());
        if (dataPeminjamDetails.getNohp() != null) dataPeminjam.setNohp(dataPeminjamDetails.getNohp());
        if (dataPeminjamDetails.getAset() != null) dataPeminjam.setAset(dataPeminjamDetails.getAset());
        if (dataPeminjamDetails.getTahunaset() != null) dataPeminjam.setTahunaset(dataPeminjamDetails.getTahunaset());
        if (dataPeminjamDetails.getAlamat() != null) dataPeminjam.setAlamat(dataPeminjamDetails.getAlamat());
        if (dataPeminjamDetails.getKota() != null) dataPeminjam.setKota(dataPeminjamDetails.getKota());
        if (dataPeminjamDetails.getKecamatan() != null) dataPeminjam.setKecamatan(dataPeminjamDetails.getKecamatan());
        if (dataPeminjamDetails.getKeterangan() != null) dataPeminjam.setKeterangan(dataPeminjamDetails.getKeterangan());
        
        // Update nama file hanya jika ada file baru yang diunggah
        if (dataPeminjamDetails.getFotoktp() != null && !dataPeminjamDetails.getFotoktp().isEmpty()) dataPeminjam.setFotoktp(dataPeminjamDetails.getFotoktp());
        if (dataPeminjamDetails.getFotobpkb() != null && !dataPeminjamDetails.getFotobpkb().isEmpty()) dataPeminjam.setFotobpkb(dataPeminjamDetails.getFotobpkb());
        if (dataPeminjamDetails.getFotostnk() != null && !dataPeminjamDetails.getFotostnk().isEmpty()) dataPeminjam.setFotostnk(dataPeminjamDetails.getFotostnk());
        if (dataPeminjamDetails.getFotokk() != null && !dataPeminjamDetails.getFotokk().isEmpty()) dataPeminjam.setFotokk(dataPeminjamDetails.getFotokk());
        if (dataPeminjamDetails.getFotorekeningkoran() != null && !dataPeminjamDetails.getFotorekeningkoran().isEmpty()) dataPeminjam.setFotorekeningkoran(dataPeminjamDetails.getFotorekeningkoran());
        if (dataPeminjamDetails.getFotorekeninglistrik() != null && !dataPeminjamDetails.getFotorekeninglistrik().isEmpty()) dataPeminjam.setFotorekeninglistrik(dataPeminjamDetails.getFotorekeninglistrik());
        if (dataPeminjamDetails.getFotobukunikah() != null && !dataPeminjamDetails.getFotobukunikah().isEmpty()) dataPeminjam.setFotobukunikah(dataPeminjamDetails.getFotobukunikah());
        if (dataPeminjamDetails.getFotosertifikat() != null && !dataPeminjamDetails.getFotosertifikat().isEmpty()) dataPeminjam.setFotosertifikat(dataPeminjamDetails.getFotosertifikat());
        if (dataPeminjamDetails.getFotoktppenjamin() != null && !dataPeminjamDetails.getFotoktppenjamin().isEmpty()) dataPeminjam.setFotoktppenjamin(dataPeminjamDetails.getFotoktppenjamin());
                    
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
    
    public DataPeminjam dataLengkap(String id) {
        DataPeminjam dataPeminjam = dataPeminjamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data Peminjam not found with id: " + id));

        Status lengkapStatus = new Status();
        lengkapStatus.setIdstatus("S005"); 
        dataPeminjam.setStatus(lengkapStatus);

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

    public DataPeminjam cairkanPeminjaman(String id) {
        DataPeminjam dataPeminjam = dataPeminjamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Data Peminjam not found with id: " + id));

        Status cairStatus = new Status();
        cairStatus.setIdstatus("S003"); 
        dataPeminjam.setStatus(cairStatus);
        dataPeminjam.setTglpencairan(LocalDate.now());

        return dataPeminjamRepository.save(dataPeminjam);
    }
}