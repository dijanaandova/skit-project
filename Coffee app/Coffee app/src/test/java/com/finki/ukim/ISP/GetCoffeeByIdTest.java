package com.finki.ukim.ISP;

import com.finki.ukim.domain.Coffee;
import com.finki.ukim.domain.enumerations.CoffeeSize;
import com.finki.ukim.repository.CoffeeRepository;
import com.finki.ukim.service.CoffeeService;
import com.finki.ukim.service.CoffeeServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetCoffeeByIdTest {

    private CoffeeService coffeeService;
    private CoffeeRepository coffeeRepository;

    @BeforeEach
    public void init(){
        this.coffeeRepository = mock(CoffeeRepository.class);
        this.coffeeService = new CoffeeServiceImplementation(coffeeRepository);
    }

    //C1 = We have a coffee with an ID

    //C1 = T
    @Test
    public void getCoffeeByIdTest1(){

        Coffee expectedCoffee = new Coffee(5L, "Oatmilk Shaken Espresso", CoffeeSize.MEDIUM, 8.0, true);

        when(coffeeRepository.findById(5L)).thenReturn(Optional.of((expectedCoffee)));

        //System.out.println(expectedCoffee);
        Coffee actualCoffee = this.coffeeService.getCoffeeById(5L);
        //System.out.println(actualCoffee);
        assertEquals(expectedCoffee, actualCoffee);

    }

    //C1 = F
    @Test
    public void getCoffeeByIdTest2(){

        when(coffeeRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> coffeeService.getCoffeeById(1L));

    }
}
