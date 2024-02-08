package com.finki.ukim.Integration;

import com.finki.ukim.CoffeeAppApplication;
import com.finki.ukim.controller.CoffeeController;
import com.finki.ukim.domain.Coffee;
import com.finki.ukim.domain.enumerations.CoffeeSize;
import com.finki.ukim.service.CoffeeService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(CoffeeController.class)
public class CoffeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CoffeeService coffeeService;

    @Test
    public void testViewHomePage() throws Exception {
        List<Coffee> coffeeList = new ArrayList<>();
        coffeeList.add(new Coffee(5L, "Oatmilk Shaken Espresso", CoffeeSize.LARGE, 12.0, true));
        Page<Coffee> page = new PageImpl<>(coffeeList);
        when(coffeeService.getAllCoffees()).thenReturn(coffeeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(model().attributeExists("listCoffees"));
    }


    @Test
    public void testShowNewCoffeeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/showNewCoffeeForm"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("new_coffee"))
                .andExpect(model().attributeExists("coffee"));
    }

    @Test
    public void testAddNewCoffee() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/saveCoffee")
                        .param("name", "Oatmilk Shaken Espresso")
                        .param("size", String.valueOf(CoffeeSize.MEDIUM))
                        .param("price", String.valueOf(10.0))
                        .param("hasCaffeine", String.valueOf(true)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void testEditCoffeeForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/saveCoffee")
                        .param("name", "Oatmilk Shaken Espresso")
                        .param("size", String.valueOf(CoffeeSize.LARGE))
                        .param("price", String.valueOf(10.0))
                        .param("hasCaffeine", String.valueOf(true)))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());

        Coffee coffee = new Coffee(5L, "Oatmilk Shaken Espresso", CoffeeSize.LARGE, 10.0, true);
        when(coffeeService.getCoffeeById(5L)).thenReturn(coffee);

        mockMvc.perform(MockMvcRequestBuilders.get("/showFormForUpdate/5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("update_coffee"))
                .andExpect(model().attribute("coffee", coffee));
    }

    @Test
    public void testDeleteCoffee() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/saveCoffee")
                    .param("name", "Oatmilk Shaken Espresso")
                    .param("size", "LARGE")
                    .param("price", "10.0")
                        .param("hasCaffeine", "true"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection());


        Coffee addedCoffee = new Coffee(1L, "Oatmilk Shaken Espresso", CoffeeSize.LARGE, 10.0, true);
        when(coffeeService.getCoffeeById(1L)).thenReturn(addedCoffee);

        mockMvc.perform(MockMvcRequestBuilders.get("/delete/1"))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(coffeeService, times(1)).deleteCoffeeById(1L);

        List<Coffee> coffeeList = new ArrayList<>();
        coffeeList.add(new Coffee(2L, "Pumpikn Spice Latte", CoffeeSize.MEDIUM, 9.0, false));
        when(coffeeService.getAllCoffees()).thenReturn(coffeeList);

        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(model().attribute("listCoffees", hasSize(1)));
    }


}
