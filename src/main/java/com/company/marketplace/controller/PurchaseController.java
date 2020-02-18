package com.company.marketplace.controller;

import com.company.marketplace.model.Purchase;
import com.company.marketplace.repository.PurchaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/purchases"})
public class PurchaseController {

    private final PurchaseRepository repository;

    /**
     *
     * @return
     */
    @GetMapping
    public List findAll() {
        return repository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Purchase> findById(@PathVariable long id) {
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
    public Purchase create(@RequestBody Purchase purchase) {
        return repository.save(purchase);
    }


}
