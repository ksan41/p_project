package com.cafe.shop.domain.token.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDto {
    
    private String refreshToken;
    private String accessToken;
}
