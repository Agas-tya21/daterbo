package com.example.daterbo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "users") // Menggunakan "users" karena "user" adalah keyword di beberapa DB
public class User {

    @Id
    private String iduser;

    private String namauser;

    private String email;
    
    private String password;

}