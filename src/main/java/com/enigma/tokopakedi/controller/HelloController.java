package com.enigma.tokopakedi.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    private List<String> fruits = List.of("Apel", "Mangga", "Mangga Muda", "Mangga Tua", "Nanas", "Nanas Muda");

    @GetMapping(path = "/fruits")
    public List<String> getList(@RequestParam String search){
        List<String> temp = new ArrayList<>();

        List<String> result = fruits.stream()
                .filter(fruit -> fruit.toLowerCase().contains(search.toLowerCase())).toList();
        return result;

    }

    private List<Map<String, Object>> users = List.of(
            Map.of(
                    "id", 1,
                    "name", "budi"
            ),
            Map.of(
                    "id", 2,
                    "name", "man"
            )
    );
    @GetMapping(path = "/users/{userId}")
    public Map<String, Object> getUserById(@PathVariable Integer userId){
        Map<String, Object> temp = null;
        for (Map<String, Object> user : users)
            if (user.get("id").equals(userId)){
                temp = user;
            }
        return temp;
    }

    @GetMapping(path = "/")
    public String helloWorld(){
        return "Hello world";
    }
    @GetMapping(path = "/fruit")
    public List<String> getLists(){
        return List.of("Apple", "Mango", "Orange");
    }
    @GetMapping(path = "/cars")
    public Map<String, Object> getCars(){
        return Map.of(
                "brand", "toyota",
                "name", "supra",
                "year", 2020
        );
    }

    @PostMapping(path = "/users")
    public User postUser(@RequestBody User user){
        return user;
    }
}
