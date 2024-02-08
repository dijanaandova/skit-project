package com.finki.ukim.service;

import com.finki.ukim.domain.Coffee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CoffeeService {

     List<Coffee> getAllCoffees();
     Coffee saveCoffee(Coffee coffee);
    Coffee getCoffeeById(Long id);

    void deleteCoffeeById(Long id);

    boolean isKidsDrink(Coffee coffee);


}
