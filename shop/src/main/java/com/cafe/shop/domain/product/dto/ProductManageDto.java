package com.cafe.shop.domain.product.dto;

import java.util.Date;

import com.cafe.shop.domain.manager.Manager;
import com.cafe.shop.domain.product.Product;
import com.cafe.shop.domain.product.vo.Money;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class ProductManageDto {

    private int price;
    private int costPrice;
    private Date expirationDate;
    private String category;
    private String productName;
    private String discription;
    private String size;

    public Product toEntity(String barcode, Manager manager) {
        return Product.builder()
                .manager(manager)
                .price(new Money(this.price))
                .costPrice(new Money(this.costPrice))
                .expirationDate(this.expirationDate)
                .category(this.category)
                .productName(this.productName)
                .discription(this.discription)
                .barcode(barcode)
                .size(this.getSize())
                .build();
    }
}
