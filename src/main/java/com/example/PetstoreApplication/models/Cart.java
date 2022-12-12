package com.example.PetstoreApplication.models;


import jakarta.persistence.*;

@Entity
// @Table(name = "cart")
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "person_id")
  private Long personId;

  @Column(name = "product_id")
  private Long productId;

  public Cart(Long personId, Long productId) {
    this.personId = personId;
    this.productId = productId;
  }

  public Cart() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPersonId() {
    return personId;
  }

  public void setPersonId(Long personId) {
    this.personId = personId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }
}