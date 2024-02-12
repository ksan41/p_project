package com.payhere.shop.domain.product.dto;

import java.util.Date;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ProductInfoDto extends ProductManageDto{
    
    private Long productId;
    private String managerId;
    private String barcode;
    
    public ProductInfoDto() {
        super();
    }
    
    @Builder
    public ProductInfoDto(Long productId, String managerId, String barcode,
        int price, int costPrice, Date expirationDate, String category, String productName,
        String discription, String size
    ) {
        setPrice(price);
        setCostPrice(costPrice);
        setExpirationDate(expirationDate);
        setCategory(category);
        setProductName(productName);
        setDiscription(discription);
        setSize(size);
        this.productId = productId;
        this.managerId = managerId;
        this.barcode = barcode;
    }

}
