package com.company.marketplace.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItem {

    private Product product;
    private Integer quantity;

    public BigDecimal getSubTotal() {
        return getProduct().getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
