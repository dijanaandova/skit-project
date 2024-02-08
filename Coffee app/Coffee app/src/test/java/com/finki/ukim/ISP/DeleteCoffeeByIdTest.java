package com.finki.ukim.ISP;

import com.finki.ukim.domain.Coffee;
import com.finki.ukim.domain.enumerations.CoffeeSize;
import com.finki.ukim.repository.CoffeeRepository;
import com.finki.ukim.service.CoffeeService;
import com.finki.ukim.service.CoffeeServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class DeleteCoffeeByIdTest {

    private CoffeeService coffeeService;
    private CoffeeRepository coffeeRepository;

    @BeforeEach
    public void init(){
        this.coffeeRepository = mock(CoffeeRepository.class);
        this.coffeeService = new CoffeeServiceImplementation(coffeeRepository);
    }

    //C1 = We successfully delete a coffee by its ID

    //C1 = T
    @Test
    public void deleteCoffeeByIdTest1(){

        long coffeeId = 5L;
        Coffee coffeeToBeDeleted = new Coffee(coffeeId, "Oatmilk Shaken Espresso", CoffeeSize.MEDIUM, 8.0, true);

        when(coffeeRepository.findById(coffeeId)).thenReturn(Optional.of(coffeeToBeDeleted));

        Coffee coffeeDeleted = coffeeService.getCoffeeById(coffeeId);

        assertEquals(coffeeToBeDeleted, coffeeDeleted);
    }

    //C1 = F
    @Test
    public void deleteCoffeeByIdTest2(){

        long coffeeId = 5L;

        when(coffeeRepository.findById(coffeeId)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> coffeeService.getCoffeeById(5L));
    }
}
