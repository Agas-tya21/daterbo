package com.example.daterbo.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "surveyor")
public class Surveyor {

    @Id
    private String id;
    private String namasurveyor;
    private String nowa;
    private String namaleasing;
    private String asalleasing;

    public Surveyor() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }
}