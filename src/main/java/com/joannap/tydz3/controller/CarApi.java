package com.joannap.tydz3.controller;


import com.joannap.tydz3.model.Car;
import com.joannap.tydz3.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cars")
public class CarApi {

    private CarService carService;

    @Autowired
    public CarApi(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public ResponseEntity<List<Car>> getCars() {
        return new ResponseEntity<>(carService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable("id") long id) {
        Optional<Car> car = carService.getCarById(id);
        if (car.isPresent()) {
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/color/{color}")
    public ResponseEntity<Car> getCarByColor(@PathVariable("color") String color) {
        Optional<Car> car = carService.getCarByColor(color);
        if (car.isPresent()) {
            return new ResponseEntity<>(car.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping
    public ResponseEntity<Long> addCar (@RequestBody Car car) {
        Car addedCar = carService.addCar(car);
        return new ResponseEntity<>(addedCar.getId(), HttpStatus.CREATED);
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
        Optional<Car> car = carService.modifyCarParameter(id,newMark,newColor, newModel);
        if (car.isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @PutMapping
    public ResponseEntity modifyCar(@RequestBody Car newCar) {
        Optional<Car> carModified = carService.modifyCar(newCar);
        if (carModified.isPresent()) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        boolean deleted = carService.removeCar(id);
        if (deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

}
