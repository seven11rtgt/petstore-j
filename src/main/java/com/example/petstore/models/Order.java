package com.example.petstore.models;

import com.example.petstore.enumm.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.time.LocalDateTime;

@Entity
// @Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String orderNumber;

  @Min(value = 0, message = "Количество товара не может быть отрицательным")
  private int quantity;

  private float price;

  private LocalDateTime dateTime;

  @ManyToOne(optional = false)
  private Product product;

  @ManyToOne(optional = false)
  private Person person;

  private Status status;

  public Order() {
  }

  public Order(String orderNumber, Product product, Person person, int quantity, float price, Status status) {
    this.orderNumber = orderNumber;
    this.product = product;
    this.person = person;
    this.quantity = quantity;
    this.price = price;
    this.status = status;
  }


  // Заполняем дату и время при создании объекта класса
  @PrePersist
  private void init(){
    dateTime = LocalDateTime.now();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getOrderNumber() {
    return orderNumber;
  }

  public void setOrderNumber(String orderNumber) {
    this.orderNumber = orderNumber;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public float getPrice() {
    return price;
  }

  public void setPrice(float price) {
    this.price = price;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
