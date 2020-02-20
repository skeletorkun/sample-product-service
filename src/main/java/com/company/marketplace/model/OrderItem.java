package com.company.marketplace.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItem {

    private final Product product;
    private final Integer quantity;
    private BigDecimal subTotal;

    public OrderItem(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.subTotal = calculateSubTotal(product, quantity);
    }

    public BigDecimal calculateSubTotal(Product product, Integer quantity) {
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
