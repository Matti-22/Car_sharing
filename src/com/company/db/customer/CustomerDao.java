package com.company.db.customer;

import com.company.db.car.Car;

import java.util.List;

public interface CustomerDao {
    public List<Customer> getAllCustomers();
    public void addCustomer(Customer customer);
    public void rentCar(Customer customer, Car car);
    public void returnCar(Customer customer);
    public void deleteCustomer(Customer customer);
}
