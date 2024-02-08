package com.finki.ukim.controller;

import com.finki.ukim.domain.Coffee;
import com.finki.ukim.service.CoffeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class CoffeeController {

    @Autowired
    CoffeeService coffeeService;


    @GetMapping("/")
    public String viewHomePage(Model model) {

        model.addAttribute("listCoffees", coffeeService.getAllCoffees());
        return "index";
    }


    @GetMapping("/showNewCoffeeForm")
    public String showNewEmployeeForm(Model model) {
        Coffee coffee = new Coffee();
        model.addAttribute("coffee", coffee);
        return "new_coffee";
    }

    @PostMapping("/saveCoffee")
    public String saveEmployee(@ModelAttribute("coffee") Coffee coffee) {
        coffeeService.saveCoffee(coffee);
        return "redirect:/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model) {
        Coffee coffee = coffeeService.getCoffeeById(id);
        model.addAttribute("coffee", coffee);
        return "update_coffee";
    }

    @GetMapping("/delete/{id}")
    public String deleteEmployeeById(@PathVariable Long id, Model model) {
        coffeeService.deleteCoffeeById(id);
        return "redirect:/";
    }


}
