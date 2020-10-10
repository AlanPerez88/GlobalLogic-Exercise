package com.aperez.exercise.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Date created;
    private Date updated;
    private Date lastLogin;
    private String token;
    private boolean isActive;

    @PrePersist
    private void prePersist() {
        this.created = new Date();
    }

    @PreUpdate
    private void preUpdate() {
        this.updated = new Date();
    }
}
