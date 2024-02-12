package com.payhere.shop.domain.manager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.payhere.shop.domain.manager.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, String>{

    boolean existsByPhone(String phone);

    Optional<Manager> findByPhone(String phone);
    
} 