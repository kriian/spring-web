package com.geekbrains.spring.web.repositories;

import com.geekbrains.spring.web.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrderByUser_Username(String username);
}
