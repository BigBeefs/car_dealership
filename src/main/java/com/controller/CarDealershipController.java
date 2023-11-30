package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

	/*
	 * Input da dare nel body: {"vatNumber": "AA123456789", "name": "SigAuto2", "country" :
	 * "Tasmania", "city" : "Taz", "cars": [ { "plate": "GG333CC", "model": "M3", "price" : 2
	 * }]}
	 * 
	 * http://127.0.0.1:8080/api/createCarsDealership
	 */
	@PostMapping("/createCarsDealership")
	public Map<Dealership, List<Car>> saveCarsDealership2(@RequestBody Dealership dealership) {
		return cdService.insertCarsDealership2(dealership);
	}

	/*
	 * Input da dare nel body: [ { "plate": "GG333HH", "model": "M5", "price" : 23 }]
	 * 
	 * http://127.0.0.1:8080/api/updateCarsOfDealership/{vatNumber}
	 */
	@PutMapping("/updateCarsOfDealership/{vatNumber}")
	public Map<Dealership, List<Car>> updateCarsOfDealership(@PathVariable String vatNumber,
			@RequestBody List<Car> cars) {
		return cdService.updateCarsOfDealership(vatNumber, cars);
	}

	/*
	 * Input da dare nel body: "vatNumber": "AA123456789"
	 * 
	 * http://127.0.0.1:8080/api/deleteDealershipWithCars
	 */
	@DeleteMapping("/deleteDealershipWithCars")
	public Map<Boolean, String> deleteDealershipWithCars(@RequestBody Dealership dealership) {
		return cdService.deleteDealershipWithCars(dealership);
	}

	/*
	 * http://127.0.0.1:8080/api/findAllCarDealrships
	 */
	@GetMapping("/findAllCarDealrships")
	public List<Dealership> findAllCarDealrships() {
		return cdService.findAllCarDealrships();
	}

	/*
	 * Input da dare nel body:{ "vatNumber": "AA123456789" }
	 * 
	 * http://127.0.0.1:8080/api/findAllCarDealerships
	 */
	@GetMapping("/findDealershipByVatNumber")
	public Dealership findDealershipByVatNumber(@RequestBody Dealership dealership) {
		return cdService.findDealershipByVatNumber(dealership);
	}
}
