package com.company.db.car;

import com.company.db.company.Company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarDaoImpl implements CarDao {
    private final Connection connection;

    public CarDaoImpl(Connection connection) {
        this.connection = connection;
        String sqlQuery = "CREATE TABLE IF NOT EXISTS CAR " +
                "(ID INTEGER NOT NULL AUTO_INCREMENT, " +
                "NAME VARCHAR UNIQUE NOT NULL, " +
                "COMPANY_ID INTEGER NOT NULL, " +
                "IS_RENT BOOLEAN DEFAULT FALSE," +
                "PRIMARY KEY ( ID ), " +
                "CONSTRAINT FK_COMPANY FOREIGN KEY (COMPANY_ID) REFERENCES COMPANY(ID));";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlQuery);
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getAllCars(Company company) {
        List<Car> cars = new ArrayList<>();
        try{
            Statement statement = this.connection.createStatement();
            String sqlQuery = "SELECT * FROM CAR";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                if(resultSet.getInt("COMPANY_ID") == company.getID()){
                    Car car = new Car(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("COMPANY_ID"), resultSet.getBoolean("IS_RENT"));
                    cars.add(car);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cars;
    }

    @Override
    public void addCar(Car car) {
        try{
            Statement statement = this.connection.createStatement();
            String sqlQuery = String.format("INSERT INTO CAR (NAME, COMPANY_ID) VALUES ('%s', %s)", car.getNAME(), car.getCOMPANY_ID()) ;
            statement.execute(sqlQuery);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCar(Car car) {
        try{
            Statement statement = this.connection.createStatement();
            String sqlQuery = String.format("DELETE FROM CAR WHERE ID=%s", car.getID()) ;
            statement.execute(sqlQuery);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateIsRateStatus(Car car, Integer action) {
        try{
            Statement statement = this.connection.createStatement();
            if(action == 1){
                String sqlQuery = String.format("UPDATE CAR SET IS_RENT = TRUE WHERE ID=%s", car.getID()) ;
                statement.execute(sqlQuery);
            }if(action == 2){
                String sqlQuery = String.format("UPDATE CAR SET IS_RENT = FALSE WHERE ID=%s", car.getID()) ;
                statement.execute(sqlQuery);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
