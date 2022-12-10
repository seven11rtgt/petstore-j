package com.example.petstore.util;

import com.example.petstore.models.Person;
import com.example.petstore.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
  private final PersonService personService;

  @Autowired
  public PersonValidator(PersonService personService) {
    this.personService = personService;
  }

  // Указываем, для какой модели предназначен данный валидатор
  @Override
  public boolean supports(Class<?> clazz) {
    return Person.class.equals(clazz);
  }

  // Прописываем правила валидации
  @Override
  public void validate(Object target, Errors errors) {
    Person person = (Person) target;

    if (personService.findByLogin(person) != null) {
      errors.rejectValue("login", "loginAlreadyExistsError", "Данный логин уже занят");
    }
  }
}