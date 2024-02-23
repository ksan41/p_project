package com.cafe.shop.domain.product.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.cafe.shop.domain.product.dto.ProductManageDto;

@Service
public class ProductValidator {
    
    public String validateProductData(ProductManageDto addData) {
        StringBuilder sb = new StringBuilder();

        sb.append(checkRequiredData(addData));
        sb.append(checkPrice(addData));

        return sb.toString();
    }

    private String checkRequiredData(ProductManageDto addData) {

        if (!StringUtils.hasText(addData.getProductName())) {
            return "상품명을 입력해주세요.\n";
        }
        if (!StringUtils.hasText(addData.getCategory())) {
            return "카테고리명을 입력해주세요.\n";
        }

        return "";
    }

    private String checkPrice(ProductManageDto addData) {
        if (addData.getPrice() < 0 || addData.getCostPrice() < 0) {
            return "가격은 0 이상의 값을 입력해주세요.\n";
        }
        if (addData.getPrice() < addData.getCostPrice()) {
            return "원가는 가격 이하의 값을 입력해주세요.\n";
        }

        return "";
    }
}
