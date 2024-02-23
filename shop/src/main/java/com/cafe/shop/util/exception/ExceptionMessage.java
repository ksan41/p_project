package com.cafe.shop.util.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExceptionMessage {
    
    NOT_ALLOWED_MANAGER("잘못된 사용자 정보입니다."),
    PRODUCT_NOT_FOUND("상품 정보를 찾을 수 없었습니다.")
    ;

    private String message;

    public String get() {
        return this.message;
    }
}
