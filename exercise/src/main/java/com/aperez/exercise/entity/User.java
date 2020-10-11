package com.aperez.exercise.entity;

import com.aperez.exercise.validator.Password;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    @Password
    private String password;

    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    private String token;

    @Temporal(TemporalType.DATE)
    private Date created;

    @Temporal(TemporalType.DATE)
    private Date modified;

    @Temporal(TemporalType.DATE)
    private Date lastLogin;

    private boolean isActive = true;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Set<Phone> phones;

    @PrePersist
    private void prePersist(){
        this.created = new Date();
    }

    @PreUpdate
    private void preUpdate(){
        this.modified = new Date();
    }



}
