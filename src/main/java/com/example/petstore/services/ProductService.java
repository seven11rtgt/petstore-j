package com.example.petstore.services;

import com.example.petstore.models.Product;
import com.example.petstore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {
  private final ProductRepository productRepository;

  @Autowired
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  // Получаем все товары
  public List<Product> getAllProduct() {
    return productRepository.findAll();
  }

  // Получаем товар по id
  public Product getProductId(Long id) {
    Optional<Product> optionalProduct = productRepository.findById(id);
    return optionalProduct.orElse(null);
  }

  // Сохраняем товар
  @Transactional
  public void saveProduct(Product product) {
    productRepository.save(product);
  }

  // Обновляем данные товара
  @Transactional
  public void updateProduct(Long id, Product product) {
    product.setId(id);
    productRepository.save(product);
  }

  // Удаляем товар по id
  @Transactional
  public void deleteProduct(Long id) {
    productRepository.deleteById(id);
  }
}