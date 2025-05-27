package com.ssafy.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @Column(unique = true)
    private String email;

    private String password;
    private String name;

    private String role;

    private String sido;
    private String gugun;
    private String dong;
    
    @PrePersist
    public void prePersist() {
        if (this.role == null) {
            this.role = "USER";
        }
    }
}
