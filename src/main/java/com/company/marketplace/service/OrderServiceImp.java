package com.company.marketplace.service;

import com.company.marketplace.dto.CreateOrderDTO;
import com.company.marketplace.dto.CreateOrderItemDTO;
import com.company.marketplace.exception.InvalidOrderException;
import com.company.marketplace.exception.ProductNotFoundException;
import com.company.marketplace.model.Order;
import com.company.marketplace.model.OrderItem;
import com.company.marketplace.model.Product;
import com.company.marketplace.repository.OrderRepository;
import com.company.marketplace.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.company.marketplace.model.Order.MAX_ALLOWED_QUANTITY;


@AllArgsConstructor
@Service
public class OrderServiceImp implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public Order create(CreateOrderDTO createOrderDTO){
        List<CreateOrderItemDTO> dtoOrderItems = createOrderDTO.getOrderItems();

//        dtoOrderItems.stream().map(i -> { new OrderItem(this.checkInput(i), i.getQuantity()))

        List<OrderItem> items = new ArrayList<>();
        for (CreateOrderItemDTO i : dtoOrderItems){
            Product productInRepository = checkInput(i);
            OrderItem orderItem = new OrderItem(productInRepository, i.getQuantity());
            items.add(orderItem);
        }

        return orderRepository.save(new Order(createOrderDTO.getUserEmail(), items));
    }

    /**
     * Applies some checks on the order item input
     * @param itemDTO
     * @return
     */
    private Product checkInput(CreateOrderItemDTO itemDTO) {
        // check if the product exists in db
        Long id = itemDTO.getProductId();
        Optional<Product> productInRepository = productRepository.findById(id);
        if(!productInRepository.isPresent()){
            throw new ProductNotFoundException("Please create the product first #id:" + id);
        }

        // check quantity
        if(itemDTO.getQuantity() < 1 || itemDTO.getQuantity() > MAX_ALLOWED_QUANTITY){
            String err = String.format("Invalid quantity (%d) on product #id%d", itemDTO.getQuantity(), id);
            throw new InvalidOrderException(err);
        }

        return productInRepository.get();
    }
}
