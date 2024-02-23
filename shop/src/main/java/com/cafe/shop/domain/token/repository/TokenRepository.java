package com.cafe.shop.domain.token.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cafe.shop.domain.token.Token;

public interface TokenRepository extends JpaRepository<Token, String> {
    
}
