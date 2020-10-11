package com.aperez.exercise.repository;

import com.aperez.exercise.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where u.email = :email and u.password = :password")
    User findByEmailPassword(@Param("email") String email, @Param("password") String password);

}
