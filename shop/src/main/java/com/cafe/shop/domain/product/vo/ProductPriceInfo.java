package com.cafe.shop.domain.product.vo;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor
@Getter
public class ProductPriceInfo {
    
    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "price"))
    private Money price;
    
    @AttributeOverride(name = "value", column = @Column(name = "cost_price"))
    @Embedded
    private Money costPrice;
    
    
    @Builder
    public ProductPriceInfo(Money price, Money costPrice) {
        this.price = price;
        this.costPrice = costPrice;
    }

}
