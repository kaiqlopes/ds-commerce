package com.studying.dscommerce.repositories;

import com.studying.dscommerce.entities.OrderItem;
import com.studying.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
