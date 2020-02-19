package com.company.marketplace.controller;

import com.company.marketplace.model.Order;
import com.company.marketplace.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/purchases"})
public class OrderController {

    private final OrderRepository repository;

    /**
     *
     * @return
     */
    @GetMapping
    public List findAll() {
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Order> findById(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Place a new Order
     * @param purchase
     * @return
     */
    @PostMapping
    public Order create(@RequestBody Order purchase) {
        return repository.save(purchase);
    }


}
