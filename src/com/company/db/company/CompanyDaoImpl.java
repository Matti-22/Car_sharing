package com.company.db.company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class CompanyDaoImpl implements CompanyDao {
    private final Connection connection;

    public CompanyDaoImpl(Connection connection){
        this.connection = connection;
        String sqlCompany = "CREATE TABLE IF NOT EXISTS COMPANY " +
                "(ID INTEGER NOT NULL AUTO_INCREMENT," +
                "NAME VARCHAR(255) UNIQUE NOT NULL," +
                "PRIMARY KEY ( ID ))";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(sqlCompany);
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public List<Company> getAllCompanies() {
        List<Company> companies = new ArrayList<>();
        try{
            Statement statement = this.connection.createStatement();
            String sqlQuery = "SELECT * FROM COMPANY";
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()){
                Company company = new Company(resultSet.getInt("ID"), resultSet.getString("NAME"));
                companies.add(company);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public void addCompany(Company company) {
        try{
            List<Company> companies = getAllCompanies();
            Statement statement = this.connection.createStatement();
            if(companies.size() == 0){
                String sqlQuery = "ALTER TABLE COMPANY ALTER COLUMN ID RESTART WITH 1";
                statement.execute(sqlQuery);
            }
            String sqlQuery = String.format("INSERT INTO COMPANY (NAME) VALUES ('%s')", company.getNAME()) ;
            statement.execute(sqlQuery);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCompany(Company company) {
        try{
            Statement statement = this.connection.createStatement();
            String sqlQuery = String.format("DELETE FROM COMPANY WHERE ID=%s", company.getID()) ;
            statement.execute(sqlQuery);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

}
