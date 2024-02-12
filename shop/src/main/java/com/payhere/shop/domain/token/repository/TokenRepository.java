package com.payhere.shop.domain.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payhere.shop.domain.token.Token;

public interface TokenRepository extends JpaRepository<Token, String> {
    
}
