package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.customException.DealershipAlreadyExistsException;
import com.customException.DealershipTeapot;
import com.entity.Car;
import com.entity.Dealership;
import com.repository.CarRepository;
import com.repository.DealershipRepository;

@Service
// Abbiamo bisogno di mettere il @Transactional poichè la gestione delle transazioni non viene fatta automaticamente da spring
// Così facendo in caso di errore nell'inserimento nel db viene effettuato il rollback
@Transactional
public class CarDealershipServiceImpl implements CarDealershipService {

	@Autowired
	private DealershipRepository dealRep;

	@Autowired
	private CarRepository carRep;

	// Metodo senza mappa passando la lista di cars attravenrso la classe Dealership
	@Override
	public Map<Dealership, List<Car>> insertCarsDealership2(Dealership dealership) {
		Map<Dealership, List<Car>> result = new HashMap<>();

		String vatNumber = dealership.getVatNumber();

		List<Car> cars = dealership.getCars();

		// Controlliamo che non esista gia' il concessionario nel DB
		if (dealRep.existsById(vatNumber)) {

			throw new DealershipAlreadyExistsException("Dealership already exists");

		}

		dealership.setCars(null);

		dealRep.save(dealership);

		// Ad ogni car della lista associamo il dealership
		cars.forEach(c -> {
			c.setDealership(dealership);
		});

		carRep.saveAll(cars);

		result.put(dealership, cars);

		return result;
	}

	@Override
	public Map<Dealership, List<Car>> updateCarsOfDealership(String vatNumber, List<Car> cars) {
		Map<Dealership, List<Car>> result = new HashMap<>();
		Dealership dealershipTemp = null;

		if (!dealRep.existsById(vatNumber)) {
			throw new DealershipTeapot("Dealership Not Fund");
		}

		dealershipTemp = dealRep.findById(vatNumber).get();

		Dealership dealership = dealershipTemp;

		dealRep.save(dealership);

		cars.forEach(c -> {
			c.setDealership(dealership);
		});

		carRep.saveAll(cars);

		result.put(dealership, cars);

		return result;
	}

	@Override
	public Map<Boolean, String> deleteDealershipWithCars(Dealership dealership) {
		Map<Boolean, String> result = new HashMap<>();
		String vatNumber = dealership.getVatNumber();
		Boolean exist = dealRep.existsById(vatNumber);

		if (!exist) {
			result.put(exist, "Non trovato! Fareshi");
			return result;
		}

		// dealership = dealRep.findById(vatNumber).get();

		dealRep.deleteById(vatNumber);

		result.put(true, "Eliminato!");

		return result;
	}

	@Override
	public List<Dealership> findAllCarDealrships() {

		List<Dealership> dealerships = dealRep.findAll();

		// Per ogni dealership settiamo la sua lista di cars tramite il metodo
		// findAllByVatNumber
		// che restituisce la lista di cars
		dealerships.forEach(d -> d.setCars(carRep.findAllByDealership(d)));

		return dealerships;
	}

	@Override
	public Dealership findDealershipByVatNumber(Dealership dealership) {
		dealership = dealRep.findById(dealership.getVatNumber()).get();

		dealership.setCars(carRep.findAllByDealership(dealership));

		return dealership;
	}

	/*
	 * In base all'operazione fatta sul DB (INSERT / UPDATE) restituiamo un messaggio diverso
	 * sull'header
	 */
	@Override
	public ResponseEntity<Dealership> saveDealershipTuMadr(Dealership dealership) {
		String vatNumber = dealership.getVatNumber();
		Boolean exist = dealRep.existsById(vatNumber);
		HttpHeaders headersResponse = new HttpHeaders();

		// caso update
		if (exist) {
			headersResponse.add("Operation Type", "UPDATE");
			dealRep.save(dealership);
			return ResponseEntity.ok().headers(headersResponse).body(dealership);
		}
		// caso insert
		headersResponse.add("Operation Type", "INSERT");
		dealRep.save(dealership);
		return ResponseEntity.ok().headers(headersResponse).body(dealership);
	}

}
