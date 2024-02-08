package com.finki.ukim.logicCoverage;

import com.finki.ukim.domain.Coffee;
import com.finki.ukim.domain.enumerations.CoffeeSize;
import com.finki.ukim.service.CoffeeService;
import com.finki.ukim.service.CoffeeServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class isKidsDrinkTest {

    private CoffeeService coffeeService;

    @BeforeEach
    public void init(){
        this.coffeeService = new CoffeeServiceImplementation(null);
    }

    //hascafeine = a, price = b, size = c
    // T T F
    @Test
    public void test2(){
        Coffee coffee = new Coffee();
        coffee.setHasCaffeine(false);
        coffee.setPrice(8);
        coffee.setSize(CoffeeSize.LARGE);
        assertTrue(this.coffeeService.isKidsDrink(coffee));
    }

    //T F T rez=T
    @Test
    public void test3(){
        Coffee coffee = new Coffee();
        coffee.setHasCaffeine(false);
        coffee.setPrice(25);
        coffee.setSize(CoffeeSize.SMALL);
        assertTrue(this.coffeeService.isKidsDrink(coffee));
    }

    //T F F rez=F
    @Test
    public void test4(){
        Coffee coffee = new Coffee();
        coffee.setHasCaffeine(false);
        coffee.setPrice(25);
        coffee.setSize(CoffeeSize.LARGE);
        assertFalse(this.coffeeService.isKidsDrink(coffee));
    }

    //F T F rez=F
    @Test
    public void test5(){
        Coffee coffee = new Coffee();
        coffee.setHasCaffeine(true);
        coffee.setPrice(25);
        coffee.setSize(CoffeeSize.LARGE);
        assertFalse(this.coffeeService.isKidsDrink(coffee));
    }

}
