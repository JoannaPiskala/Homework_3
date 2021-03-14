package com.joannap.tydz3.service;

import com.joannap.tydz3.model.Car;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class CarService {

    private List<Car> carsList;

    public CarService() {
        this.carsList = new ArrayList<>();
        carsList.add(new Car(1L, "AUDI", "A3", "WHITE"));
        carsList.add(new Car(2L, "FIAT", "126P", "RED"));
    }

    public List<Car> findAll() {
        return carsList;

    }

    public Optional<Car> getCarById(long id) {
        return carsList.stream().filter(car -> car.getId() == id).findFirst();
    }


    public Optional<Car> getCarByColor(String color) {
        return carsList.stream().filter(car -> car.getColor().equals(color)).findFirst();
    }


    public Car addCar(Car car) {
        car.setId(getNextId());
        carsList.add(car);
        return car;
    }


    public Optional<Car> modifyCarParameter(long id, Optional<String> newMark, Optional<String> newColor, Optional<String> newModel) {
        Optional<Car> carModifyPar = carsList.stream().filter(car -> car.getId() == id).findFirst();
        carModifyPar.get().setMark(newMark.orElseGet(() -> carModifyPar.get().getMark()));
        carModifyPar.get().setColor(newColor.orElseGet(() -> carModifyPar.get().getColor()));
        carModifyPar.get().setModel(newModel.orElseGet(() -> carModifyPar.get().getModel()));
        return carModifyPar;
    }


    public Optional<Car> modifyCar(Car newCar) {
        Optional<Car> carModify = carsList.stream().filter(car -> car.getId() == newCar.getId()).findFirst();
            carsList.remove(carModify.get());
            carsList.add(newCar);
            return carModify;
        }


    public boolean removeCar(long id) {
        Optional<Car> first = carsList.stream().filter(car -> car.getId() == id).findFirst();
        if (first.isPresent()) {
            carsList.remove(first.get());
            return carsList.remove(first.get());
        }
        return false;
    }


    private Long getNextId() {
        if(carsList.size() != 0) {
            return carsList.stream()
                    .mapToLong(Car::getId)
                    .max()
                    .getAsLong() + 1;
        }
        return 1L;
    }

}
