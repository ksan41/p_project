package com.cafe.shop.domain.product.vo;

import java.util.Date;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class ProductInfo {
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;
    
    private String category;
    private String productName;
    private String discription;
    private String barcode;
    private String size;
    
    
    @Builder
    public ProductInfo(Date expirationDate, String category, String productName, String discription, String barcode, String size) {
        this.expirationDate = expirationDate;
        this.category = category;
        this.productName = productName;
        this.discription = discription;
        this.barcode = barcode;
        this.size = size;
    }

}
