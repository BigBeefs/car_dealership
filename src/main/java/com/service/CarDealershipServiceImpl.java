package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Car;
import com.entity.Dealership;
import com.repository.CarRepository;
import com.repository.DealershipRepository;

@Service
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
			System.out.println("Esiste gia!");
			return null;
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

		if (!dealRep.existsById(vatNumber) || vatNumber == null) {
			System.out.println("Non esiste");
			return null;
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
		System.err.println("Qua: Limoni ");

		// Per ogni dealership settiamo la sua lista di cars tramite il metodo findAllByVatNumber
		// che restituisce la lista di cars
		dealerships.forEach(d -> d.setCars(carRep.findAllByDealership(d)));

//		dealerships.forEach(d -> System.out.println(d.getCars()));

		return dealerships;
	}

	@Override
	public Map<Dealership, List<Car>> findCarDealrshipByVatNumber(Dealership dealership) {
		// TODO Auto-generated method stub
		return null;
	}

}
