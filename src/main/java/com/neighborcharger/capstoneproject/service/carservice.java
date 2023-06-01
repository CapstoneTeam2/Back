package com.neighborcharger.capstoneproject.service;

import com.neighborcharger.capstoneproject.model.car;
import com.neighborcharger.capstoneproject.repository.carRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class carservice {
    @Autowired
    carRepository carrepository;

    public double findCapacity(String car_name){
        car wantCar = carrepository.findByname(car_name).orElseGet(car::new);
        return wantCar.getCapacity();
    }

    public void InsertCar() {
        car Car = new car();

        Car.setName("아이오닉");
        Car.setCapacity(104.4);

        carrepository.save(Car);
    }
}
