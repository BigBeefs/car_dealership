package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entity.Car;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
	List<Car> findAllByVatNumber(String vatNumber);
}
