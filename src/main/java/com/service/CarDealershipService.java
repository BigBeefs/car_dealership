package com.service;

import java.util.List;
import java.util.Map;

import com.entity.Car;
import com.entity.Dealership;

public interface CarDealershipService {

	/**
	 * Inserisce un nuovo dealersship e le sue macchine
	 * 
	 * @param dealership
	 * @return Map<Dealership, List<Car>>
	 */
	Map<Dealership, List<Car>> insertCarsDealership2(Dealership dealership);

	/**
	 * Aggiorna della lista cars del dealership
	 * 
	 * @param vatNumber
	 * @param cars
	 * @return
	 */
	Map<Dealership, List<Car>> updateCarsOfDealership(String vatNumber, List<Car> cars);

	/**
	 * Elimina un dealership
	 * 
	 * @param dealership
	 * @return
	 */
	Map<Boolean, String> deleteDealership(Dealership dealership);

	/**
	 * Seleziona tutti i dearlships presenti nel DB
	 * 
	 * @return
	 */
	Map<Dealership, List<Car>> findAllCarDealrships();

	/**
	 * Seleziona il dealership e la sua lista di cars, in base all'input dato
	 * 
	 * @param dealership
	 * @return
	 */
	Map<Dealership, List<Car>> findCarDealrshipByVatNumber(Dealership dealership);
}
