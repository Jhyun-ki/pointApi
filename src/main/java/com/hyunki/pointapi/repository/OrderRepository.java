package com.hyunki.pointapi.repository;

import com.hyunki.pointapi.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
