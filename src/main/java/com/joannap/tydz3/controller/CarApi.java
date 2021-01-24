package com.joannap.tydz3.controller;


import com.joannap.tydz3.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarApi {

    private List<Car> carsList;

    public CarApi() {
        this.carsList = new ArrayList<>();
        carsList.add(new Car(1L, "AUDI", "A3", "WHITE"));
        carsList.add(new Car(2L, "FIAT", "126P", "RED"));
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        return new ResponseEntity<>(carsList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable long id) {
        Optional<Car> first = carsList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{color}")
    public ResponseEntity<Car> getCarByColor(@PathVariable String color) {
        Optional<Car> first = carsList.stream().filter(video -> video.getColor() == color).findFirst();
        if (first.isPresent()) {
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity addCar(@RequestBody Car car) {
        boolean add = carsList.add(car);
        if (add) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
/*
// To change a single parameter
   @PutMapping("/{id}")
    public ResponseEntity modifyCarParameter(@PathVariable long id, @RequestParam String newParam) {
       Optional<Car> first = carsList.stream().filter(car -> car.getId() == id).findFirst();
       if (first.isPresent()) {
           first.get().setMark(newParam);
           return new ResponseEntity(HttpStatus.OK);
       }
       return new ResponseEntity(HttpStatus.NOT_FOUND);
   }
 */

    @PutMapping("/{id}")
    public ResponseEntity modifyCarParameter(@PathVariable long id, @RequestParam Optional<String> newMark,
                                             @RequestParam Optional<String> newColor, @RequestParam Optional<String> newModel) {
        Optional<Car> first = carsList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            first.get().setMark(newMark.orElseGet(() -> first.get().getMark()));
            first.get().setColor(newColor.orElseGet(() -> first.get().getColor()));
            first.get().setModel(newModel.orElseGet(() -> first.get().getModel()));
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PutMapping
    public ResponseEntity modifyCar(@RequestBody Car newCar) {
        Optional<Car> first = carsList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();
        if (first.isPresent()) {
            carsList.remove(first.get());
            carsList.add(newCar);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity removeCar(@PathVariable long id) {
        Optional<Car> first = carsList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            carsList.remove(first.get());
            return new ResponseEntity<>(first.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
