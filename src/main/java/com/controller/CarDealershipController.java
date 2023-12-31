package com.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Car;
import com.entity.Dealership;
import com.service.CarDealershipService;

import jakarta.validation.Valid;

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
	public Map<Dealership, List<Car>> saveCarsDealership2(@Valid @RequestBody Dealership dealership) {

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
	public Map<Boolean, String> deleteDealershipWithCars(@Valid @RequestBody Dealership dealership) {
		return cdService.deleteDealershipWithCars(dealership);
	}

	/*
	 * http://127.0.0.1:8080/api/findAllCarDealrships
	 */
	@GetMapping(path = "/findAllCarDealrships")
	public List<Dealership> findAllCarDealrships() {
		return cdService.findAllCarDealrships();
	}

	/*
	 * Input da dare nel body:{ "vatNumber": "AA123456789" }
	 * 
	 * http://127.0.0.1:8080/api/findAllCarDealerships
	 */
	@GetMapping("/findDealershipByVatNumber")
	public Dealership findDealershipByVatNumber(@Valid @RequestBody Dealership dealership) {
		return cdService.findDealershipByVatNumber(dealership);
	}

	/*
	 * http://127.0.0.1:8080/api/example
	 * 
	 * metodo di test sugli hearders
	 */
	@GetMapping("/example")
	public String exampleEndpoint(@RequestHeader HttpHeaders headers) {

		System.out.println(headers);

		String acceptHeader = headers.getFirst("Accept");

		HttpHeaders headersResponse = new HttpHeaders();
		headersResponse.add("Tua madre", "Cuoca");

		// Puoi anche aggiungere altri header, se necessario
		// headersResponse.add("Another-Header", "anotherHeaderValue");

		// Restituisci la risposta con l'header personalizzato

		return " " + ResponseEntity.ok().headers(headersResponse).body("Il valore dell'header è: " + acceptHeader);
	}

	/*
	 * http://127.0.0.1:8080/api/findAllCarDealrshipsCustom1
	 * 
	 * Metodo di find all con controlli sugli headers
	 */
	@GetMapping(path = "/findAllCarDealrshipsCustom1") // produces = MediaType.APPLICATION_JSON_VALUE
	public ResponseEntity<List<Dealership>> findAllCarDealrshipsCustom1(@RequestHeader HttpHeaders headers) {

		String acceptHeader = headers.getFirst("Accept");

		List<Dealership> dealerships = cdService.findAllCarDealrships();

		Boolean checkJSON = acceptHeader.contains("application/json");
		Boolean checkXML = acceptHeader.contains("application/xml");

		if (checkXML) {

			return ResponseEntity.ok().contentType(MediaType.APPLICATION_XML).body(dealerships);

		}

		return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(dealerships);

	}

	/*
	 * http://127.0.0.1:8080/api/createDealershipHeader
	 * 
	 * In base all'operazione da fare inserisco un messaggio specifico nell'hearder della
	 * Response
	 */
	@PostMapping("/createDealershipHeader")
	public ResponseEntity<Dealership> saveDealershipTuMadr(@Valid @RequestBody Dealership dealership) {

		ResponseEntity<Dealership> re = cdService.saveDealershipTuMadr(dealership);
		return re;
	}

}