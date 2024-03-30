package com.ariana.springsecuritydemo.repository;

import com.ariana.springsecuritydemo.model.Meniu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Meniu,Integer> {

}
