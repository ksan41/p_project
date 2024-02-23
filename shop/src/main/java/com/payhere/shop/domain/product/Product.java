package com.payhere.shop.domain.product;

import java.util.Date;

import com.payhere.shop.domain.manager.Manager;
import com.payhere.shop.domain.product.dto.ProductInfoDto;
import com.payhere.shop.domain.product.dto.ProductManageDto;
import com.payhere.shop.domain.product.vo.Money;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "phone")
    private Manager manager;
    
    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "price")))
    private Money price;
    
    @Embedded
    @AttributeOverrides(@AttributeOverride(name = "value", column = @Column(name = "cost_price")))
    private Money costPrice;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    private String category;
    private String productName;
    private String discription;
    private String barcode;
    private String size;

    @Builder
    public Product(Long productId, Manager manager, Money price, Money costPrice, Date expirationDate, String category, String productName, String discription, String barcode, String size) {
        this.productId = productId;
        this.manager = manager;
        this.price = price;
        this.costPrice = costPrice;
        this.expirationDate = expirationDate;
        this.category = category;
        this.productName = productName;
        this.discription = discription;
        this.barcode = barcode;
        this.size = size;
    }

    public void modyfyProductData(ProductManageDto productData) {
        this.price = new Money(productData.getPrice());
        this.costPrice = new Money(productData.getCostPrice());
        this.expirationDate = productData.getExpirationDate();
        this.category = productData.getCategory();
        this.productName = productData.getProductName();
        this.discription = productData.getDiscription();
        this.size = productData.getSize();
    }

    public ProductInfoDto toDto() {
        return ProductInfoDto.builder()
                    .productId(this.productId)
                    .managerId(this.manager.getPhone())
                    .price(this.price.getValue())
                    .costPrice(this.costPrice.getValue())
                    .expirationDate(this.expirationDate)
                    .category(this.category)
                    .productName(this.productName)
                    .discription(this.discription)
                    .barcode(this.barcode)
                    .size(this.size)
                    .build();
    }
}
