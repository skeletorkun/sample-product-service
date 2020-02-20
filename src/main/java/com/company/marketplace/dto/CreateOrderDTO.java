package com.company.marketplace.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CreateOrderDTO {

    private String userEmail;
    private List<CreateOrderItemDTO> orderItems;
}
