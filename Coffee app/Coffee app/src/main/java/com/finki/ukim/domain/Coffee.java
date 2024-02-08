package com.finki.ukim.domain;

import com.finki.ukim.domain.enumerations.CoffeeSize;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Coffee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String name;

	private CoffeeSize size;

	private double price;
	private boolean hasCaffeine;

	public Coffee() {
	}

	public Coffee(long id, String name, CoffeeSize size, Double price, Boolean hasCaffeine){
		this.id = id;
		this.name = name;
		this.size = size;
		this.price = price;
		this.hasCaffeine = hasCaffeine;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public CoffeeSize getSize() {
		return size;
	}

	public void setSize(CoffeeSize size) {
		this.size = size;
	}

	public boolean getHasCaffeine() {
		return hasCaffeine;
	}

	public void setHasCaffeine(boolean hasCaffeine) {
		this.hasCaffeine = hasCaffeine;
	}
}
