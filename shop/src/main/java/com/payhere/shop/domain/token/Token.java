package com.payhere.shop.domain.token;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Token {
    
    @Id
    private String managerId;

    private String refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date deleteDate;

    @Builder
    public Token(String managerId, String refreshToken, Date deleteDate) {
        this.managerId = managerId;
        this.refreshToken = refreshToken;
        this.deleteDate = deleteDate;
    }
}
