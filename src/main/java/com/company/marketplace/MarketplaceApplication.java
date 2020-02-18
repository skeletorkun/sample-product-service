package com.company.marketplace;

import com.company.marketplace.model.Product;
import com.company.marketplace.model.Purchase;
import com.company.marketplace.repository.PurchaseRepository;
import com.company.marketplace.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.stream.LongStream;

@SpringBootApplication
public class MarketplaceApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketplaceApplication.class, args);
    }

    @Bean
	CommandLineRunner init(PurchaseRepository purchaseRepository, ProductRepository productRepository) {
        return args -> {
            productRepository.deleteAll();
            LongStream.range(1, 11)
                    .mapToObj(i -> {
                        Product p = new Product();
                        p.setName("Product " + i);
                        p.setPrice( i % 2 == 0 ? BigDecimal.TEN : new BigDecimal("7.00"));
                        return p;
                    })
                    .map(productRepository::save)
                    .forEach(System.out::println);

            purchaseRepository.deleteAll();
            LongStream.range(1, 11)
                    .mapToObj(i -> {
                        Purchase p = new Purchase();
                        p.setUserEmail("john.doe@sample.com");
                        p.setTotal( i % 2 == 0 ? BigDecimal.TEN : new BigDecimal("7.00"));
                        return p;
                    })
                    .map(purchaseRepository::save)
                    .forEach(System.out::println);
        };
    }
}
