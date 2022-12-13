package com.example.PetstoreApplication.repositories;

import com.example.PetstoreApplication.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByPersonId(Long id);

  // Находим заказ по последним 4 знакам без учета регистра
  // List<Product> findByOrderNumberRegex(String orderNumber);
}