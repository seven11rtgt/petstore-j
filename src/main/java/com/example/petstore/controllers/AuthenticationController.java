package com.example.petstore.controllers;

import com.example.petstore.models.Person;
import com.example.petstore.services.PersonService;
import com.example.petstore.util.PersonValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
  private final PersonValidator personValidator;
  private final PersonService personService;
  @Autowired
  public AuthenticationController(PersonValidator personValidator, PersonService personService) {
    this.personValidator = personValidator;
    this.personService = personService;
  }

  @GetMapping("/login")
  public String login(){
    return "authentication/login";
  }

  @GetMapping("/registration")
  public String registration(@ModelAttribute("person") Person person)
  {
    return "authentication/registration";
  }

  @PostMapping("/registration")
  public String resultRegistration(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult)
  {
    // Если валидатор возвращает ошибку, помещаем ее в bindingResult
    personValidator.validate(person, bindingResult);
    if(bindingResult.hasErrors()) { return "authentication/registration"; }
    personService.register(person);
    return "redirect:/index";
  }

  /*  @Controller
    public static class ProductController {
    public String products() {
      return "product/product";
    }
  }*/
}

// http:localhost:8080/authentication/login