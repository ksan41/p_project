package com.payhere.shop.util.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Getter;

@JsonFormat(shape = Shape.OBJECT)
@AllArgsConstructor
@Getter
public enum MetaData {

    SUCCESS(200,"ok"),
    /**
     * 에러 메시지, Manager 관련
     */
    NOT_ALLOWED_MANAGER(403, "허용되지 않은 접근입니다."),
    MANAGER_PARAMETER_WRONG(400, "사용자 입력값을 확인해주세요."),
    MANAGER_LOGIN_FAILED(400, "로그인에 실패했습니다. 전화번호와 비밀번호를 확인해주세요."),
    MANAGER_DUPLICATED(400, "중복된 전화번호 입니다."),
    /**
     * 에러 메시지, Product 관련
     */
    PRODUCT_PARATETER_WRONG(400, "상품 정보 입력값을 확인해주세요."),
    PRODUCT_NOT_FOUND(400, "상품 정보를 찾을 수 없습니다.")
    ;

    private int code;
    private String message;
}

