package com.example.PetstoreApplication.repositories;

import com.example.PetstoreApplication.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
  Optional<Person> findByLogin(String login);
  Optional<Person> findById(Long id);
  @Query(value = "select * from person order by id asc ", nativeQuery = true)
  List<Person> getAllUsersSortedAsc();
}