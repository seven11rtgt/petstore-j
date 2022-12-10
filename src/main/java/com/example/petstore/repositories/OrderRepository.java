package com.example.petstore.repositories;

import com.example.petstore.models.Order;
import com.example.petstore.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  List<Order> findByPerson(Person person);
}