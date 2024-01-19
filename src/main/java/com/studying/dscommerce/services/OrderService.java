package com.studying.dscommerce.services;

import com.studying.dscommerce.dtos.OrderDTO;
import com.studying.dscommerce.dtos.OrderItemDTO;
import com.studying.dscommerce.entities.*;
import com.studying.dscommerce.repositories.OrderItemRepository;
import com.studying.dscommerce.repositories.OrderRepository;
import com.studying.dscommerce.repositories.ProductRepository;
import com.studying.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    private OrderRepository repository;

    private UserService userService;

    private ProductRepository productRepository;

    private OrderItemRepository orderItemRepository;

    private AuthService authService;

    @Autowired
    public OrderService(OrderRepository repository, UserService userService, ProductRepository productRepository, OrderItemRepository orderItemRepository, AuthService authService) {
        this.repository = repository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.authService = authService;
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));
        authService.validateSelfOrAdmin(order.getClient().getId());
        return new OrderDTO(order);
    }

    @Transactional
    public OrderDTO insert(OrderDTO dto) {

        Order order = new Order();

        order.setMoment(Instant.now());
        order.setStatus(OrderStatus.WAITING_PAYMENT);

        User user = userService.authenticated();
        order.setClient(user);

        for (OrderItemDTO itemDto : dto.getItems()) {
            Product product = productRepository.getReferenceById(itemDto.getProductId());
            OrderItem item = new OrderItem(order, product, itemDto.getQuantity(), product.getPrice());
            order.getItems().add(item);
        }

        repository.save(order);
        orderItemRepository.saveAll(order.getItems());

        return new OrderDTO(order);
    }
}
