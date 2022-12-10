package com.example.petstore.repositories;

import com.example.petstore.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CartRepository extends JpaRepository<Cart, Long> {

  // Получаем корзину по id пользователя
  List<Cart> findByPersonId(Long id);

  void deleteCartByProductId(Long id);
}

