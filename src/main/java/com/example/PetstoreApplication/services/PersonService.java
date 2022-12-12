package com.example.PetstoreApplication.services;

import com.example.PetstoreApplication.models.Person;
import com.example.PetstoreApplication.repositories.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
  private final PersonRepository personRepository;
  private final PasswordEncoder crypt;

  //@Autowired
  public PersonService(PersonRepository personRepository, PasswordEncoder crypt) {
    this.personRepository = personRepository;
    this.crypt = crypt;
  }

  @Bean
  public static PasswordEncoder encoder() {
    return new BCryptPasswordEncoder();
  }

  // Получаем всех пользователей
  public List<Person> getAllUsers() {
    return personRepository.findAll();
  }

  public List<Person> getAllUsersSorted() {
    return personRepository.getAllUsersSortedAsc();
  }

  public Person findByLogin(Person person) {
    Optional<Person> person_db = personRepository.findByLogin(person.getLogin());
    return person_db.orElse(null);
  }

  public Person findById(Long id) {
    Optional<Person> person_db = personRepository.findById(id);
    return person_db.orElse(null);
  }

  @Transactional
  public void register(Person person) {
    person.setPassword(crypt.encode(person.getPassword()));
    person.setRole("ROLE_USER");
    personRepository.save(person);
  }

  // Получаем роль
  public void getRole(Person person) {
    person.getRole();
  }

  // Изменяем роль
  @Transactional
  public void setRole(Person person, String role) {
    if(role == "ROLE_USER" || role == "ROLE_ADMIN")
    {
      person.setRole(role);
      personRepository.save(person);
    }
  }
}
