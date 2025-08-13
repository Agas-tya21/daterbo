package com.example.daterbo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    
    private String nohp;

    @ManyToOne
    @JoinColumn(name = "idrole", referencedColumnName = "idrole")
    private Role role;

}