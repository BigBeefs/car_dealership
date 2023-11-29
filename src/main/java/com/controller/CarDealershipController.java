package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Car;
import com.entity.Dealership;
import com.service.CarDealershipService;

@RestController

@RequestMapping("/api")
public class CarDealershipController {

	@Autowired
	CarDealershipService cdService;

	@PostMapping("/createCarsDealership")
	public Map<Dealership, List<Car>> saveCarsDealership2(@RequestBody Dealership dealership) {
		System.err.println("Controller : " + dealership);
		return cdService.insertCarsDealership2(dealership);
	}
}
