package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	
	private final PersonRepository repository;
	
	public DemoApplication(PersonRepository repository) {
		this.repository = repository;
	}

	public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
 
    @Override
    public void run(String... args) {
		Person person = new Person("Max", "Verstappen");
		
		repository.getPersonByForename("Max")
			.ifPresentOrElse(
					System.out::println,
					() -> System.out.println(
							repository.save(person)
					)
			);
    }
}
