package com.first.firstservice;

import com.first.firstservice.entities.Customer;
import com.first.firstservice.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import java.util.List;

@SpringBootApplication
public class FirstServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstServiceApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(CustomerRepository customerRepository,
											   RepositoryRestConfiguration restConfiguration){
		return args -> {
			restConfiguration.exposeIdsFor(Customer.class);
		customerRepository.saveAll(
				List.of(
						Customer.builder().name("customer1").email("customer1@mail.mail").build(),
						Customer.builder().name("customer2").email("customer2@mail.mail").build(),
						Customer.builder().name("customer2").email("customer3@mail.mail").build()
				)
		);
		customerRepository.findAll().forEach(c->System.out.println(c));
		};
	}

}
