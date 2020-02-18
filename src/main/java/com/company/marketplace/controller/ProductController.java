package com.company.marketplace.controller;

import com.company.marketplace.model.Product;
import com.company.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping({"/products"})
public class ProductController {

    private final ProductRepository repository;

    /**
     * Get a list of all products
     * @return
     */
    @GetMapping
    public List findAll() {
        return repository.findAll();
    }

    /**
     * Get a product by its id
     * @param id
     * @return
     */
    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Product> findById(@PathVariable long id) {
        return repository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Create a new Product
     * @param product
     * @return
     */
    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }

    /**
     * Update an existing Product
     * @param id
     * @param product
     * @return
     */
    @PutMapping(value = "/{id}")
    public ResponseEntity<Product> update(@PathVariable("id") long id,
                                          @RequestBody Product product) {
        return repository.findById(id)
                .map(record -> {
                    record.setName(product.getName());
                    record.setPrice(product.getPrice());
                    Product updated = repository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }


}
