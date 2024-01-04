package com.project1.domain.shopping.order.repository;

import com.project1.domain.shopping.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository  extends JpaRepository<Order, Long> {
    List<Order> findOrderByMember_Name(String name);
}
