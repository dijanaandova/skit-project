package com.finki.ukim.service;

import java.util.List;
import java.util.Optional;

import com.finki.ukim.domain.Coffee;
import com.finki.ukim.domain.enumerations.CoffeeSize;
import com.finki.ukim.repository.CoffeeRepository;
import org.springframework.stereotype.Service;


@Service
public class CoffeeServiceImplementation implements  CoffeeService{

	private final CoffeeRepository coffeeRepository;
	public CoffeeServiceImplementation(CoffeeRepository coffeeRepository) {
		this.coffeeRepository = coffeeRepository;
	}

	public List<Coffee> getAllCoffees() {

		return coffeeRepository.findAll();
	}

	public Coffee saveCoffee(Coffee coffee) {
		return this.coffeeRepository.save(coffee);
	}

	public Coffee getCoffeeById(Long id) {
		Optional<Coffee> optionalCoffee = coffeeRepository.findById(id);
		Coffee coffee = null;
		if (optionalCoffee.isPresent()){
			coffee = optionalCoffee.get();
		}else {
			throw new RuntimeException("Coffee not found for id :: " + id);
		}

		return coffee;
	}

	public void deleteCoffeeById(Long id) {

		coffeeRepository.deleteById(id);

	}

	@Override
	public boolean isKidsDrink(Coffee coffee) {
		return (coffee.getHasCaffeine() == false && coffee.getPrice() < 15) ||
				(coffee.getHasCaffeine() == false && (coffee.getSize() == CoffeeSize.SMALL));
	}
}
