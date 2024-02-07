package com.payhere.shop.domain.product;

import java.util.Date;

import com.payhere.shop.domain.manager.Manager;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
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

@Entity
@Builder
public class Product {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long productId;

    @ManyToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
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
    private int barcode;
    private String size;

}
