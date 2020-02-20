package com.company.marketplace.service;

import com.company.marketplace.dto.CreateOrderDTO;
import com.company.marketplace.model.Order;

public interface OrderService {
    Order create(CreateOrderDTO createOrderDTO);
}
