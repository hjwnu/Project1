package com.project1.domain.shopping.order.repository;

import com.project1.domain.shopping.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
