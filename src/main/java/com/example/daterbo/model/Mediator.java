package com.example.daterbo.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "mediator")
public class Mediator {

    @Id
    private String idmediator;
    private String namamediator;
    private String asalmediator;
    private String asalleasing;
    private String nohp;

    public Mediator() {
        if (this.idmediator == null) {
            this.idmediator = UUID.randomUUID().toString();
        }
    }
}