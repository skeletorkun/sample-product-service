package com.company.marketplace.controller;

import com.company.marketplace.dto.CreateOrderDTO;
import com.company.marketplace.model.Order;
import com.company.marketplace.repository.OrderRepository;
import com.company.marketplace.service.OrderService;
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

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final OrderRepository orderRepository;
    private final OrderService orderServiceImp;

    /**
     * Return all orders for a given time period
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @GetMapping
    public List findAllByDate(@RequestParam(value = "endDate", defaultValue = "2021-01-01 00:00:00", required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime endDate,
                              @RequestParam(value = "startDate", defaultValue = "2019-01-01 00:00:00", required = false) @DateTimeFormat(pattern = DATE_TIME_FORMAT) LocalDateTime startDate) {

        return orderRepository.findByDateBetween(startDate, endDate);
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<Order> findById(@PathVariable long id) {
        return orderRepository.findById(id)
                .map(record -> ResponseEntity.ok().body(record))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Place a new Order
     *
     * @param createOrderDTO
     * @return
     */
    @PostMapping
    public Order create(@RequestBody CreateOrderDTO createOrderDTO) {
        return orderServiceImp.create(createOrderDTO);
    }


}
