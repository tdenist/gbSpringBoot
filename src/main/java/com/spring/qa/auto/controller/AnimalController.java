package com.spring.qa.auto.controller;

import com.spring.qa.auto.dto.Animal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/animals")
public class AnimalController {

    Map<Integer, Animal> animals = new HashMap<>();

    @PostConstruct
    void init(){
        animals.put(1, new Animal("Cat", false, "Barsik", 3));
        animals.put(2, new Animal("Dog", false, "Sharik", 6));
        animals.put(3, new Animal("Wolf", true, "Wolf", 7));
    }

    @GetMapping("/{id}")
    public Animal get(@PathVariable int id){
        return animals.get(id);
    }

    @GetMapping("/all")
    public List<Animal> get(){
        return new ArrayList<>(animals.values());
    }

    @PostMapping()
    public void save(@RequestBody Animal animal) {
        int id = animals.size() + 1;
        animals.put(id, animal);
    }

    @PutMapping("/{id}")
    public void change(@PathVariable int id, @RequestBody Animal animalChanging) {
        Animal animal = animals.get(id);
        animal.setAge(animalChanging.getAge());
        animal.setKind(animalChanging.getKind());
        animal.setName(animalChanging.getName());
        animal.setWild(animalChanging.getWild());
        animals.put(id, animal);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        animals.remove(id);
    }
}
