package com.example.PetstoreApplication.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {
  @Id
  //@Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  @NotEmpty(message = "Наименование товара не может быть пустым")
  private String title;

  //@Column(name = "description", nullable = false, columnDefinition = "text")
  @NotEmpty(message = "Описание товара не может быть пустым")
  private String description;

  //@Column(name = "price", nullable = false)
  @Min(value = 1, message = "Цена не может быть меньше 1 рубля")
  private float price;

  @Min(value = 1, message = "Количество не может быть меньше 1")
  private int quantity;

  //@Column(name = "warehouse", nullable = false)
  @NotEmpty(message = "Должен быть указан склад, где находится товар")
  private String warehouse;

  //@Column(name = "seller", nullable = false,columnDefinition = "text")
  @NotEmpty(message = "Должен быть указан продавец товара")
  private String seller;

  @ManyToOne(optional = false)
  private Category category;

  private LocalDateTime dateTime;

  // Заполняем дату и время при создании экзмепляра класса
  @PrePersist
  private void init(){
    dateTime = LocalDateTime.now();
  }

  @OneToMany(mappedBy = "product")
  private List<Order> orderList;

  @ManyToMany()
  @JoinTable(name = "Cart",
          joinColumns = @JoinColumn(name = "product_id"),
          inverseJoinColumns = @JoinColumn(name = "person_id"))
  private List<Person> personList;

  @OneToMany(cascade = CascadeType.ALL,
          fetch = FetchType.LAZY,
          mappedBy = "product")
  private List<Image> imageList = new ArrayList<>();

  // Метод по добавлению фото в список фото товара
  public void addImageToProduct(Image image){
    image.setProduct(this);
    imageList.add(image);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

  public String getWarehouse() {
    return warehouse;
  }

  public void setWarehouse(String warehouse) {
    this.warehouse = warehouse;
  }

  public String getSeller() {
    return seller;
  }

  public void setSeller(String seller) {
    this.seller = seller;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public List<Order> getOrderList() {
    return orderList;
  }

  public void setOrderList(List<Order> orderList) {
    this.orderList = orderList;
  }

  public List<Person> getPersonList() {
    return personList;
  }

  public void setPersonList(List<Person> personList) {
    this.personList = personList;
  }

  public List<Image> getImageList() {
    return imageList;
  }

  public void setImageList(List<Image> imageList) {
    this.imageList = imageList;
  }
}