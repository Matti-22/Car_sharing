package com.company.db.customer;

import com.company.db.car.Car;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao{
    private final Connection connection;

    public CustomerDaoImpl(Connection connection) {
        this.connection = connection;
        String sqlCompany = "CREATE TABLE IF NOT EXISTS CUSTOMER" +
                "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
                "NAME VARCHAR UNIQUE NOT NULL," +
                "RENTED_CAR_ID INTEGER DEFAULT NULL," +
                "PRIMARY KEY ( ID ), " +
                "CONSTRAINT FK_RENTED_CAR FOREIGN KEY (RENTED_CAR_ID) REFERENCES CAR(ID));";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCompany);
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        List<Customer> customers = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            String sqlQuery = "SELECT * FROM CUSTOMER";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                Customer customer = new Customer(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("RENTED_CAR_ID"));
                customers.add(customer);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void addCustomer(Customer customer) {
        try{
            Statement statement = this.connection.createStatement();
            String sqlQuery = String.format("INSERT INTO CUSTOMER (NAME) VALUES ('%s')", customer.getNAME()) ;
            statement.execute(sqlQuery);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void rentCar(Customer customer, Car car) {
        try{
            Statement statement = this.connection.createStatement();
            String sqlQuery = String.format("UPDATE CUSTOMER SET RENTED_CAR_ID=%s WHERE ID=%s", car.getID() , customer.getID());
            statement.execute(sqlQuery);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void returnCar(Customer customer) {
        try{
            Statement statement = this.connection.createStatement();
            String sqlQuery = String.format("UPDATE CUSTOMER SET RENTED_CAR_ID = NULL WHERE ID=%s", customer.getID());
            statement.execute(sqlQuery);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomer(Customer customer) {
        try{
            Statement statement = this.connection.createStatement();
            String sqlQuery = String.format("DELETE FROM CUSTOMER WHERE ID=%s", customer.getID()) ;
            statement.execute(sqlQuery);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
