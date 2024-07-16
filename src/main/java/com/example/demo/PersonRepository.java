package com.example.demo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, String> {
    
    @Query(value = "{ 'forename': { $ne: ?0 } }", sort = "{ 'forename': 1 }")
    Optional<Person> getPersonByForename(String forename);
}