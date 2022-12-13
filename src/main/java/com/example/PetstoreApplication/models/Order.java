package com.example.PetstoreApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;

@Entity
@Table(name = "order")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "order_number")
  @Length(min = 4, max=10, message = "Номер заказа должен быть от 4 до 10 " +
          "символов")
  private String orderNumber;

  @Min(value = 0, message = "Количество товара не может быть отрицательным")
  private int quantity;

  private float price;

  @Column(name = "order_date")
  private LocalDateTime dateTime;

  @ManyToOne(optional = false)
  private Product product;

  @Column(name = "customer_id")
  @ManyToOne(optional = false)
  private Person person;


  private String status;

  public Order() {
  }

  public Order(String orderNumber, Product product, Person person, int quantity, float price) {
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

/*  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }*/
}
