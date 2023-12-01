package com.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

@Entity
public class Car {

	@Id
	@Column(unique = true)
	// Accettiamo solamente questo tipo di formato tramite la regex
	// Per farla funzionare abbiamo aggiunto la dependency: spring-boot-starter-validation
	// Aggiungendo il @Vaild nel @Requestbody
	@Pattern(regexp = "^[A-Z]{2}\\d{3}[A-Z]{2}$", message = "Formato non corretto!")
	private String plate;

	private String model;

	private Double price;

	@ManyToOne
	@JoinColumn(name = "vatNumber")
	@JsonIgnore
	private Dealership dealership;

	// Insert @Pattern(regexp = "^[A-Z]{2}\\d{3}[A-Z]{2}$", message = "Formato non corretto!")
	public Car(String plate, String model, Double price) {
		this.plate = plate;
		this.model = model;
		this.price = price;
	}

	// Delete
	public Car(String plate) {
		this.plate = plate;
	}

	@Override
	public String toString() {
		return "Car [plate=" + plate + ", model=" + model + ", price=" + price + "]";
	}

}
