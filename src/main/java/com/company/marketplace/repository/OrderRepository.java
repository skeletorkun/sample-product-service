package com.company.marketplace.repository;

import com.company.marketplace.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, Long> {

    List<Order> findByDateBetween(LocalDateTime from, LocalDateTime to);
}
