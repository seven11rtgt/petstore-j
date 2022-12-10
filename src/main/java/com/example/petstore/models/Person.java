package com.example.petstore.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
// @Table(name = "Person")
public class Person {
  @Id
  //@Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty(message = "Логин не может быть пустым")
  @Size(min = 5, max = 50, message = "Логин должен быть от 5 до 50 символов")
  //Column(name = "login")
  private String login;

  @NotEmpty(message = "Пароль не может быть пустым")
  @Size(min = 5, max = 100, message = "Пароль должен быть от 5 до 100 символов")
 // @Column(name = "password")
//    @Pattern(regexp = "(?=^.{8,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$", message = "Пароль должен содержать не менее 8 символов, хотя бы одну цифру, спец символ, букву в верхнем регистре и в нижнем регистре")
  private String password;

  //@Column(name = "role")
  private String role;

  @ManyToMany()
  @JoinTable(name = "Cart",
          joinColumns = @JoinColumn(name = "person_id"),
          inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Product> productList;

  @OneToMany(mappedBy = "Person")
  private List<Order> orderList;

  public Person() {
  }

  public Person(Long id, String login, String password) {
    this.id = id;
    this.login = login;
    this.password = password;
  }

  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getLogin() {
    return login;
  }
  public void setLogin(String login) {
    this.login = login;
  }
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  public String getRole() {
    return role;
  }
  public void setRole(String role) {
    this.role = role;
  }
}