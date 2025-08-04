// agas-tya21/daterbo/daterbo-5d63aa6fe5be4c5f0f3509284e781c11677bf0a1/src/main/java/com/example/daterbo/model/Admin.java
package com.example.daterbo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "admin")
public class Admin {

    @Id
    private String email;

    private String namaadmin;

    private String password;

}