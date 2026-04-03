package com.example.AI_Interview_Sys.repository;

import com.example.AI_Interview_Sys.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
