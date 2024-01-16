package com.studying.dscommerce.services;

import com.studying.dscommerce.dtos.OrderDTO;
import com.studying.dscommerce.entities.Order;
import com.studying.dscommerce.repositories.OrderRepository;
import com.studying.dscommerce.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public OrderDTO findById(Long id) {
        Order order = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found"));
        return new OrderDTO(order);
    }

}
