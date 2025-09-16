package com.example.daterbo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
@Table(name = "agent")
public class Agent {

    @Id
    private String idagent;
    private String namaagent;
    private String namaleasing;
    private String asalleasing;
    private String nohp;
    
    public Agent() {
        if (this.idagent == null) {
            this.idagent = UUID.randomUUID().toString();
        }
    }
}