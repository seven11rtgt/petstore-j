package com.example.PetstoreApplication.services;

import com.example.PetstoreApplication.models.Order;
import com.example.PetstoreApplication.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
  private final OrderRepository orderRepository;

  @Autowired
  public OrderService(OrderRepository orderRepository) {
    this.orderRepository = orderRepository;
  }

  // Получаем все заказы
  public List<Order> getAllOrders() {
    return orderRepository.findAll();
  }

  // Получаем заказы конкретного клиента по его id
  public List<Order> getOrderByUserId(Long id) {
    return orderRepository.findByPersonId(id);
  }
}
