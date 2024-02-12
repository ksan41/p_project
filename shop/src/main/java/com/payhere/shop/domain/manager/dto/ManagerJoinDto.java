package com.payhere.shop.domain.manager.dto;

import com.payhere.shop.domain.manager.Manager;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerJoinDto {
    private String phone;
    private String password;
    private String name;

    public Manager toEntity(String encryptedPassword) {
        return Manager.builder()
                    .phone(this.phone)
                    .password(encryptedPassword)
                    .name(this.name)
                    .build();
    }
}
