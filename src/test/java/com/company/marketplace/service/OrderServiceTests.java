package com.company.marketplace.service;

import com.company.marketplace.dto.CreateOrderDTO;
import com.company.marketplace.dto.CreateOrderItemDTO;
import com.company.marketplace.exception.ProductNotFoundException;
import com.company.marketplace.model.Order;
import com.company.marketplace.model.OrderItem;
import com.company.marketplace.model.Product;
import com.company.marketplace.repository.OrderRepository;
import com.company.marketplace.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class OrderServiceTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    void setUp() {

        // clear repositories
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @DisplayName("given order with a valid input, when created using service, then order is saved")
    @Test
    void testCreateOrder() {

        // given
        Product p1 = createProduct("Blue T-Shirt", new BigDecimal("22"));
        Product p2 = createProduct("Red Trousers", new BigDecimal("12"));

        List<CreateOrderItemDTO> i = Arrays.asList(
                new CreateOrderItemDTO(p1.getId(), 2),
                new CreateOrderItemDTO(p2.getId(), 3));

        CreateOrderDTO input = new CreateOrderDTO("user.email@buzz.com", i);

        // when
        orderService.create(input);

        // then
        List<Order> foundOrders = orderRepository.findAll();
        assertThat(foundOrders).hasSize(1);
        List<OrderItem> items = foundOrders.iterator()
                .next()
                .getOrderItems();
        assertThat(items).hasSize(2);
        assertThat(items.toString()).isEqualTo("[" +
				"OrderItem(product=Product(id=11, name=Blue T-Shirt, price=22.00), quantity=2), " +
				"OrderItem(product=Product(id=12, name=Red Trousers, price=12.00), quantity=3)]");
    }


    @DisplayName("given order with an invalid product, when created with service, then an exception should be raised")
    @Test
    void testCreateOrderInvalidProduct() {

		// given
		List<CreateOrderItemDTO> i = Collections.singletonList(new CreateOrderItemDTO(9999L, 2));
		CreateOrderDTO input = new CreateOrderDTO("user.email@buzz.com", i);

		// when & then
		assertThrows(ProductNotFoundException.class, () -> orderService.create(input));
    }

    private Product createProduct(String name, BigDecimal price) {
        Product p = new Product();
        p.setPrice(price);
        p.setName(name);
        return productRepository.save(p);
    }
}
