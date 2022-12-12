package com.example.PetstoreApplication.security;

import com.example.PetstoreApplication.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public record PersonDetails(Person person) implements UserDetails {
  @Autowired
  public PersonDetails {
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // Возвращает лист из одного элемента
    return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
  }

  @Override
  public String getPassword() {
    return this.person.getPassword();
  }

  @Override
  public String getUsername() {
    return this.person.getLogin();
  }

  // Действительность аккаунта
  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  // Акаунт заблокирован / не заблокирован
  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  // Действительность пароля
  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  // Активность аккаунта
  @Override
  public boolean isEnabled() {
    return true;
  }
}
