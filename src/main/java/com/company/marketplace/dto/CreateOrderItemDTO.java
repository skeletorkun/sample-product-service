package com.company.marketplace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CreateOrderItemDTO {

    private final Long productId;
    private final Integer quantity;

}
