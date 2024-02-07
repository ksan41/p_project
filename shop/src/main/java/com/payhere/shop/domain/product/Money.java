package com.payhere.shop.domain.product;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Embeddable
@AllArgsConstructor
@Getter
public class Money {
    private int value;
}
