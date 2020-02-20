package com.company.marketplace.controller;

import com.company.marketplace.model.Order;
import com.company.marketplace.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/orders"})
public class OrderController {

    private final OrderRepository repository;

    /**
     * Return all orders for a given time period
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping
    public List findAllByDate(@RequestParam(value = "endDateTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime endDate,
            @RequestParam(value = "startDateTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'") LocalDateTime startDate) {

        return repository.findByDateBetween(startDate, endDate);
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Order> findById(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Place a new Order
     *
     * @param order
     * @return
     */
    @PostMapping
    public Order create(@RequestBody Order order) {
        return repository.save(order);
    }


}
