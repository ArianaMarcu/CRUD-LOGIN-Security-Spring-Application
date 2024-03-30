package com.ariana.springsecuritydemo.repository;

import com.ariana.springsecuritydemo.model.Comenzi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ComenziRepo extends JpaRepository<Comenzi,Integer> {
}
