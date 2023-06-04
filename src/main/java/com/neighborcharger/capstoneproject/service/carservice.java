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
        String[] names;
        double[] capa;
        names = new String[] {"기아 EV9", "EV6 GT", "니로 EV", "GV60", "KG 모빌리티 토레스 EVX", "아이오닉5", "아이오닉6", "EV6", "니로 EV" ,"테슬라 모델S", "BMW iX 1", "코나 일렉트릭"};
        capa = new double[] {99.8, 77.4, 64.8, 77.4, 73.4, 67.4, 64.4, 68.4, 64.8, 100, 66.5, 58.6};

        for(int i = 0; i < names.length; i++){
            car Car = new car();
            Car.setName(names[i]);
            Car.setCapacity(capa[i]);
            carrepository.save(Car);
        }
    }
}
