package com.finki.ukim.ISP;

import com.finki.ukim.domain.Coffee;
import com.finki.ukim.domain.enumerations.CoffeeSize;
import com.finki.ukim.repository.CoffeeRepository;
import com.finki.ukim.service.CoffeeService;
import com.finki.ukim.service.CoffeeServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SaveCoffeeTest {

    private CoffeeService coffeeService;
    private CoffeeRepository coffeeRepository;

    @BeforeEach
    public void init(){
        this.coffeeRepository = mock(CoffeeRepository.class);
        this.coffeeService = new CoffeeServiceImplementation(coffeeRepository);
    }

    //C1 = The coffee we want to save is not null
    //C2 = We successfully save a coffee

    //C1 = T
    //C2 = T
    @Test
    public void saveCoffeeTest1(){

        Coffee coffeeToBeSaved = new Coffee(5L, "Pumpkin Spice Latte", CoffeeSize.MEDIUM, 9.0, true);

        when(coffeeRepository.save(coffeeToBeSaved)).thenReturn(coffeeToBeSaved);
        Coffee coffeeSaved = this.coffeeService.saveCoffee(coffeeToBeSaved);
        assertEquals(coffeeToBeSaved, coffeeSaved);
    }

    //C1 = T
    //C2 = F
    @Test
    public void saveCoffeeTest2(){

        Coffee coffeeToBeSaved = new Coffee(5L, "Pumpkin Spice Latte", CoffeeSize.MEDIUM, 9.0, true);

        when(coffeeRepository.save(coffeeToBeSaved)).thenThrow(RuntimeException.class);
        assertThrows(RuntimeException.class, () -> coffeeService.saveCoffee(coffeeToBeSaved));
    }

    //C1 = F
    //C2 = F
    @Test
    public void saveCoffeeTest3(){

        Coffee coffeeToBeSaved = null;

        when(coffeeRepository.save(coffeeToBeSaved)).thenReturn(coffeeToBeSaved);
        Coffee coffeeSaved = this.coffeeService.saveCoffee(coffeeToBeSaved);
        assertNull(coffeeSaved);

    }
}
