package com.finki.ukim.ISP;

import com.finki.ukim.domain.Coffee;
import com.finki.ukim.domain.enumerations.CoffeeSize;
import com.finki.ukim.repository.CoffeeRepository;
import com.finki.ukim.service.CoffeeService;
import com.finki.ukim.service.CoffeeServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetAllCoffeesTest {

    private CoffeeService coffeeService;
    private CoffeeRepository coffeeRepository;

    @BeforeEach
    public void init(){
        this.coffeeRepository = mock(CoffeeRepository.class);
        this.coffeeService = new CoffeeServiceImplementation(coffeeRepository);
    }

    //C1 = We have a list of coffees

    //C1 = T
    @Test
    public void getAllCoffeesTest1(){

        List<Coffee> expectedCoffees = new ArrayList<>();
        expectedCoffees.add(new Coffee(1L, "Pumpkin Spice Latte", CoffeeSize.MEDIUM, 8.0, false));
        expectedCoffees.add(new Coffee(2L, "Oatmilk Shaken Espresso", CoffeeSize.LARGE, 9.0, true));

        when(coffeeRepository.findAll()).thenReturn(expectedCoffees);
        List<Coffee> actualCoffees = coffeeService.getAllCoffees();
        assertEquals(expectedCoffees.size(), actualCoffees.size());
    }

    //C1 = F
    @Test
    public void getAllCoffeesTest2(){

        List<Coffee> expectedCoffees = new ArrayList<>();

        when(coffeeRepository.findAll()).thenReturn(expectedCoffees);
        List<Coffee> actualCoffees = coffeeService.getAllCoffees();
        assertEquals(expectedCoffees.size(), actualCoffees.size());
    }
}
