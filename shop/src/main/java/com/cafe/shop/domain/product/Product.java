package com.cafe.shop.domain.product;

import com.cafe.shop.domain.manager.Manager;
import com.cafe.shop.domain.product.dto.ProductInfoDto;
import com.cafe.shop.domain.product.dto.ProductManageDto;
import com.cafe.shop.domain.product.vo.Money;
import com.cafe.shop.domain.product.vo.ProductInfo;
import com.cafe.shop.domain.product.vo.ProductPriceInfo;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Product implements Comparable<Product>{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "phone")
    private Manager manager;
    
    @Embedded
    private ProductPriceInfo priceInfo;

    @Embedded
    private ProductInfo productInfo;

    @Builder
    public Product(Long productId, Manager manager, ProductPriceInfo priceInfo, ProductInfo productInfo) {
        this.productId = productId;
        this.manager = manager;
        this.priceInfo = priceInfo;
        this.productInfo = productInfo;
    }

    @Override
    public int compareTo(Product product) {
        return this.productInfo.getProductName().compareTo(product.getProductInfo().getProductName());
    }

    public void modifyProductData(ProductManageDto productData) {
        this.priceInfo = ProductPriceInfo.builder()
                            .price(new Money(productData.getPrice()))
                            .costPrice(new Money(productData.getCostPrice()))
                            .build();
        this.productInfo = ProductInfo.builder()
                            .expirationDate(productData.getExpirationDate())
                            .category(productData.getCategory())
                            .productName(productData.getProductName())
                            .discription(productData.getDiscription())
                            .size(productData.getSize())
                            .build();
    }

    public ProductInfoDto toDto() {
        return ProductInfoDto.builder()
                    .productId(this.productId)
                    .managerId(this.manager.getPhone())
                    .price(this.priceInfo.getPrice().getValue())
                    .costPrice(this.priceInfo.getCostPrice().getValue())
                    .expirationDate(this.productInfo.getExpirationDate())
                    .category(this.productInfo.getCategory())
                    .productName(this.productInfo.getProductName())
                    .discription(this.productInfo.getDiscription())
                    .barcode(this.productInfo.getBarcode())
                    .size(this.productInfo.getSize())
                    .build();
    }
}
