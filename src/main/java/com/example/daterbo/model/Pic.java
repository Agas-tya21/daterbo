package com.example.daterbo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "pic")
public class Pic {

    @Id
    private String idpic;
    private String namapic;
    private String nohp;
    private String namaleasing;
    private String asalleasing;
}