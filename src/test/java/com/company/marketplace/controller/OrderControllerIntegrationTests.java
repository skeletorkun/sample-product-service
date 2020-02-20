package com.company.marketplace.controller;

import com.company.marketplace.model.Order;
import com.company.marketplace.model.Product;
import com.company.marketplace.repository.OrderRepository;
import com.company.marketplace.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;

import static com.jayway.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    public void setupClass() {

        RestAssured.port = port;
        orderRepository.deleteAll();
        productRepository.deleteAll();
    }

    @DisplayName("given order with two order items, when create endpoint is called, then order is saved and returned")
    @Test
    public void testCreateOrder() {

        // given
        Product p1 = createProduct("Blue T-Shirt", new BigDecimal("22"));
        Product p2 = createProduct("Red Trousers", new BigDecimal("12"));

        // when
        String body = "{\n" +
                "  \"orderItems\": [\n" +
                "    {\n" +
                "      \"productId\": " + p1.getId() + ",\n" +
                "      \"quantity\": 10\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": " + p2.getId() +",\n" +
                "      \"quantity\": 7\n" +
                "    }\n" +
                "  ],\n" +
                "  \"userEmail\": \"jeff.bezos@amazon.com\"\n" +
                "}";

        Response response = given().contentType(ContentType.JSON).body(body)
                .post("/orders")
                .then().statusCode(200)
                .extract().response();

        // then
        Order order = response.as(Order.class);
        assertThat(order).isEqualTo(orderRepository.findAll().get(0));
    }

    @DisplayName("given order with an invalid product, when create endpoint is called, then Http response 400 is returned")
    @Test
    public void testCreateOrderWithInvalidProduct() {

        // given
        Product p1 = createProduct("Blue T-Shirt", new BigDecimal("22"));

        // when
        String body = "{\n" +
                "  \"orderItems\": [\n" +
                "    {\n" +
                "      \"productId\": " + 0L + ",\n" + // invalid product id
                "      \"quantity\": 10\n" +
                "    },\n" +
                "    {\n" +
                "      \"productId\": " + p1.getId() +",\n" +
                "      \"quantity\": 7\n" +
                "    }\n" +
                "  ],\n" +
                "  \"userEmail\": \"jeff.bezos@amazon.com\"\n" +
                "}";

        Response response = given().contentType(ContentType.JSON).body(body)
                .post("/orders")
                .then().statusCode(400)
                .extract().response();

        assertThat(response.asString()).isEqualTo("Please create the product first #id:0");

        assertThat(orderRepository.findAll().size()).isEqualTo(0);
    }


    private Product createProduct(String name, BigDecimal price) {
        Product p = new Product();
        p.setPrice(price);
        p.setName(name);
        return productRepository.save(p);
    }

}
