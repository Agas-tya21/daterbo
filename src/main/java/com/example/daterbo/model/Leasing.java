package com.example.daterbo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "leasing")
public class Leasing {

    @Id
    private String idleasing;

    private String namaleasing;

}