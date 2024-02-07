package com.payhere.shop.domain.manager;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
public class Manager {
    
    @Id
    private String phone;
    private String password;
    private String name;

    @Builder
    public Manager(String phone, String password, String name) {
        this.phone = phone;
        this.password = password;
        this.name = name;
    }

}
