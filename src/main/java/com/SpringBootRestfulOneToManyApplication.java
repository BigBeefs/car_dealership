package com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.service.CarDealershipService;

@SpringBootApplication
public class SpringBootRestfulOneToManyApplication implements CommandLineRunner {

	@Autowired
	private CarDealershipService service;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootRestfulOneToManyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(service.findAllCarDealrships());
	}

}
