package com.example.petstore.models;

import jakarta.persistence.*;

@Entity
public class Image {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String fileName;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  private Product product;

  public Image(Long id, String fileName, Product product) {
    this.id = id;
    this.fileName = fileName;
    this.product = product;
  }

  public Image() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }
}
