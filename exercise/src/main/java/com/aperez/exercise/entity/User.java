package com.aperez.exercise.entity;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.criteria.Fetch;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "user")
    private Set<Phone> phones = new HashSet<>();


    @PrePersist
    private void prePersist() {
        this.created = new Date();
    }

    @PreUpdate
    private void preUpdate() {
        this.updated = new Date();
    }
}
