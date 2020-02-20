package com.company.marketplace.model;

import com.company.marketplace.exception.InvalidOrderException;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document
public class Order {

    public static final int MAX_ALLOWED_QUANTITY = 9999999;
    private String userEmail;
    private BigDecimal total;
    private LocalDateTime date;

    private List<OrderItem> orderItems;

    public Order(String userEmail, List<OrderItem> orderItems) {
        if (CollectionUtils.isEmpty(orderItems)) {
            throw new InvalidOrderException("Order must have at least one order item");
        }
        if (StringUtils.isEmpty(userEmail)) {
            throw new InvalidOrderException("An email address must be provided");
        }
        this.userEmail = userEmail;
        this.orderItems = orderItems;
        this.total = calculateTotals(orderItems);
        this.date = LocalDateTime.now();
    }

    /**
     * Calculates the total price of an order from a list of items
     *
     * @param orderItems
     * @return
     */
    private BigDecimal calculateTotals(List<OrderItem> orderItems) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderItem item : orderItems) {
            total = total.add(item.getSubTotal());
        }
        return total;
    }
}
