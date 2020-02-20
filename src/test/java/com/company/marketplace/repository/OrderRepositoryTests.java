package com.company.marketplace.repository;

import com.company.marketplace.model.Order;
import com.company.marketplace.model.OrderItem;
import com.company.marketplace.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderRepositoryTests {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@BeforeEach
	void setUp() {

		// clear repositories
		orderRepository.deleteAll();
		productRepository.deleteAll();
	}

	@DisplayName("given order with two order items, when persist using mongo repository, then order is saved")
	@Test
	void testCreateOrder() {
		// given
		Order order = prepareTestOrderWithTwoOrderItems();

		orderRepository.deleteAll();

		// when
		orderRepository.save(order);

		// then
		List<Order> foundOrders = orderRepository.findAll();
		assertThat(foundOrders).hasSize(1);
		List<OrderItem> items = foundOrders.iterator()
				.next()
				.getOrderItems();
		assertThat(items).hasSize(2);
		assertThat(items).containsOnlyElementsOf(order.getOrderItems());
	}

	private Order prepareTestOrderWithTwoOrderItems() {

		Product p = createProduct("Red t-shirt", BigDecimal.ONE);
		Product p2 = createProduct("Blue trousers", BigDecimal.TEN);

		OrderItem item1 = new OrderItem(p, 2);
		OrderItem item2 = new OrderItem(p2, 5);

		return new Order("john.doe@test.com", Arrays.asList(item1, item2));
	}

	private Product createProduct(String name, BigDecimal price){
		Product p = new Product();
		p.setPrice(price);
		p.setName(name);
		return productRepository.save(p);
	}
}
