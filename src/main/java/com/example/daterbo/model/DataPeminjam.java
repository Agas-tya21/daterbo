package com.example.daterbo.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "datapeminjam")
public class DataPeminjam {

    @Id
    private String iddatapeminjam;

    private String nik;

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    private User user;

    @ManyToOne
    @JoinColumn(name = "idstatus", referencedColumnName = "idstatus")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "idleasing", referencedColumnName = "idleasing")
    private Leasing leasing;

    private LocalDate tglinput;
    private LocalDate tglpenerimaan;
    private LocalDate tglpencairan;
    private String namapeminjam;
    private String nohp;
    private String aset;
    private String tahunaset;
    private String alamat;
    private String kota;
    private String kecamatan;

    @Column(columnDefinition = "TEXT")
    private String keterangan;

    private String fotoktp;
    private String fotobpkb;
    private String fotostnk;
    private String fotokk;
    private String fotorekeningkoran;
    private String fotorekeninglistrik;
    private String fotobukunikah;
    private String fotosertifikat;

    public DataPeminjam() {
        this.iddatapeminjam = UUID.randomUUID().toString();
    }
}