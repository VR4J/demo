package com.example.demo;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, String> {
    
    @Query(value = "{ 'forename': ?0 }", sort = "{ 'forename': 1 }")
    Optional<Person> getFirstPersonByForename(String forename);

    List<Person> findByForename(String forename);
}