package com.company.db.car;

import com.company.db.company.Company;

import java.util.List;

public interface CarDao {
    public List<Car> getAllCars(Company company);
    public void addCar(Car car);
    public void deleteCar(Car car);
    public void updateIsRateStatus(Car car, Integer action);
}
