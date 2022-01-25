package com.company;

import com.company.db.car.Car;
import com.company.db.car.CarDao;
import com.company.db.car.CarDaoImpl;
import com.company.db.company.Company;
import com.company.db.company.CompanyDao;
import com.company.db.company.CompanyDaoImpl;
import com.company.db.customer.Customer;
import com.company.db.customer.CustomerDao;
import com.company.db.customer.CustomerDaoImpl;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Menu {
    private CompanyDao companyDao;
    private CarDao carDao;
    private CustomerDao customerDao;
    private static final Scanner scanner = new Scanner(System.in);

    public void activeMenu(Connection connection){
        this.companyDao = new CompanyDaoImpl(connection);
        this.carDao = new CarDaoImpl(connection);
        this.customerDao = new CustomerDaoImpl(connection);
        loginScreen();
    }

    private void loginScreen() {
        boolean run = true;
        while (run){
            System.out.println("1. Log in as a manager");
            System.out.println("2. Log in as a customer");
            System.out.println("3. Create a customer");
            System.out.println("4. Delete a customer");
            System.out.println("0. Exit");
            String choice = scanner.nextLine();
            System.out.println();
            switch (choice) {
                case "0":
                    run = false;
                    break;

                case "1":
                    managerScreen();
                    break;

                case "2":
                    printAllCustomers();
                    break;

                case "3":
                    addCustomerToDB();
                    break;

                case "4":
                    deleteCustomerFromDB();
                    break;

                default:
                    System.out.println("Wrong value!!!\n");
            }
        }
    }

    private void managerScreen(){
        boolean run = true;
        while (run) {
            System.out.println("1. Company list");
            System.out.println("2. Create a company");
            System.out.println("3. Delete a company");
            System.out.println("0. Back");
            String choice = scanner.nextLine();
            System.out.println();
            switch (choice) {
                case "0":
                    run = false;
                    break;

                case "1":
                    printAllCompanyManager(); //1 = manager
                    break;

                case "2":
                    addNewCompanyToDB();
                    break;

                case "3":
                    deleteCompanyFromDB();
                    break;

                default:
                    System.out.println("Wrong value!!!");

            }
        }
    }

    private void printAllCompanyManager(){
        List<Company> companies = companyDao.getAllCompanies();
        boolean run = true;
        int index = 1;
        if(companies.size() > 0){
            while (run){
                System.out.println("Choose the company:");
                for(Company company : companies) {
                    System.out.println(index + ". " + company.getNAME());
                    index++;
                }
                System.out.println("0. Back");
                String choice = scanner.nextLine();
                index = 1;
                for(Company company : companies) {
                    if (Integer.valueOf(choice).equals(index)) {
                        companyScreen(company);
                        run = false;
                        break;
                    }
                    index++;
                }
                if ("0".equals(choice)) {
                    run = false;
                }
                System.out.println();
            }
        }else{
            System.out.println("The company list is empty!\n");
        }
    }

    private void addNewCompanyToDB(){
        System.out.println("Enter the company name:");
        String companyName = scanner.nextLine();
        Company company = new Company(companyName);
        companyDao.addCompany(company);
        System.out.println("The company was created!\n");
    }

    private void deleteCompanyFromDB(){
        List<Company> companies = companyDao.getAllCompanies();
        boolean run = true;
        int index = 1;
        if(companies.size() > 0){
            while (run){
                System.out.println("Choose the company to want to delete:");
                for(Company company : companies) {
                    System.out.println(index + ". " + company.getNAME());
                    index++;
                }
                System.out.println("0. Back");
                String choice = scanner.nextLine();
                index = 1;
                for(Company company : companies) {
                    if (Integer.valueOf(choice).equals(index)) {
                        List<Car> cars = carDao.getAllCars(company);
                        for (Car car : cars){
                            carDao.deleteCar(car);
                        }
                        companyDao.deleteCompany(company);
                        System.out.println("\nYou delete: " + company.getNAME());
                        run = false;
                        break;
                    }
                    index++;
                }
                if ("0".equals(choice)) {
                    run = false;
                }
                System.out.println();
            }
        }else{
            System.out.println("The company list is empty!\n");
        }
    }

    private void deleteCarFromDB(Company company){
        List<Car> cars = carDao.getAllCars(company);
        boolean run = true;
        int index = 1;

        if(cars.size() > 0){
            while (run){
                System.out.println("\nChoose a car to want to delete:");
                for(Car car : cars) {
                    System.out.println(index + ". " + car.getNAME());
                    index++;
                }
                System.out.println("0. Back");
                String choice = scanner.nextLine();
                index = 1;
                for(Car car : cars) {
                    if(Integer.valueOf(choice).equals(index)){
                        carDao.deleteCar(car);
                        System.out.println("\nYou delete: " + car.getNAME());
                        run = false;
                        break;
                    }
                    index++;
                }
                if("0".equals(choice)){
                    run = false;
                }
                System.out.println();
            }
        }else {
        System.out.println("The car list is empty!");
        }
    }

    private void deleteCustomerFromDB(){
        List<Customer> customers = customerDao.getAllCustomers();
        List<Company> companies = companyDao.getAllCompanies();
        boolean run = true;
        int index = 1;
        if(customers.size() > 0){
            while (run){
                System.out.println("Choose a customer to want to delete:");
                for(Customer customer : customers) {
                    System.out.println(index + ". " + customer.getNAME());
                    index++;
                }
                System.out.println("0. Back");
                String choice = scanner.nextLine();
                index = 1;
                for(Customer customer : customers) {
                    if (Integer.valueOf(choice).equals(index)) {
                        for(Company company : companies) {
                            List<Car> cars = carDao.getAllCars(company);
                            for (Car car : cars) {
                                if (Objects.equals(car.getID(), customer.getRENTED_CAR_ID())) {
                                    customerDao.returnCar(customer);
                                    carDao.updateIsRateStatus(car, 2); // Rent = false (2)
                                    car.setRent(false);
                                }
                            }
                        }
                        customerDao.deleteCustomer(customer);
                        System.out.println("\nYou delete: " + customer.getNAME());
                        run = false;
                        break;
                    }
                    index++;
                }
                if ("0".equals(choice)) {
                    run = false;
                }
                System.out.println();
            }
        }else{
            System.out.println("The customer list is empty!\n");
        }
    }

    private void companyScreen(Company company){
        System.out.format("\n'%s' company\n",company.getNAME());
        boolean run = true;
        while (run) {
            System.out.println("1. Car list");
            System.out.println("2. Create a car");
            System.out.println("3. Delete a Car");
            System.out.println("0. Back");
            String choice = scanner.nextLine();
            switch (choice) {
                case "0":
                    run = false;
                    break;

                case "1":
                    System.out.println();
                    printAllCarsInCompany(company);
                    System.out.println();
                    break;

                case "2":
                    addNewCarToDB(company);
                    break;

                case "3":
                    deleteCarFromDB(company);
                    break;

                default:
                    System.out.println("Wrong value!!!\n");
            }
        }
    }

    private void printAllCarsInCompany(Company company){
        List<Car> cars = carDao.getAllCars(company);
        int index = 1;
        if(cars.size() > 0){
            System.out.println("Car list:");
            for(Car car : cars) {
                System.out.println(index + ". " + car.getNAME());
                index++;
            }
        }else {
            System.out.println("The car list is empty!");
        }
    }

    private void addNewCarToDB(Company company){
        System.out.println("\nEnter the car name:");
        String carName = scanner.nextLine();
        Car car = new Car(carName, company.getID(), false);
        carDao.addCar(car);
        System.out.println("The car was added!\n");
    }

    private void addCustomerToDB(){
        System.out.println("Enter the customer name:");
        String customerName = scanner.nextLine();
        Customer customer = new Customer(customerName);
        customerDao.addCustomer(customer);
        System.out.println("The customer was added!\n");
    }

    private void printAllCustomers(){
        List<Customer> customers = customerDao.getAllCustomers();
        boolean run = true;
        int index = 1;
        if(customers.size() > 0){
            while (run){
                System.out.println("Customer list:");
                for(Customer customer : customers) {
                    System.out.println(index + ". " + customer.getNAME());
                    index++;
                }
                System.out.println("0. Back");
                String choice = scanner.nextLine();
                index = 1;
                for(Customer customer : customers) {
                    if (Integer.valueOf(choice).equals(index)) {
                        customerScreen(customer);
                        run = false;
                        break;
                    }
                    index++;
                }
                if ("0".equals(choice)) {
                    run = false;
                }
                System.out.println();
            }
        }else{
            System.out.println("The customer list is empty!\n");
        }
    }

    private void customerScreen(Customer customer){
        boolean run = true;
        while (run){
            System.out.println("\n1. Rent a car");
            System.out.println("2. Return a rented car");
            System.out.println("3. My rented car");
            System.out.println("0. Back");
            String choice = scanner.nextLine();
            switch (choice) {
                case "0":
                    run = false;
                    break;

                case "1":
                    printAllCompanyCustomer(customer);
                    break;

                case "2":
                    returnCar(customer);
                    break;

                case "3":
                    printRentCar(customer);
                    break;

                default:
                    System.out.println("Wrong value!!!\n");
            }
        }
    }

    private void printAllCompanyCustomer(Customer customer){
        List<Company> companies = companyDao.getAllCompanies();
        boolean run = true;
        int index = 1;
        if(customer.getRENTED_CAR_ID() == 0){
            if(companies.size() > 0){
                while (run){
                    System.out.println("\nChoose a company:");
                    for(Company company : companies) {
                        System.out.println(index + ". " + company.getNAME());
                        index++;
                    }
                    System.out.println("0. Back");
                    String choice = scanner.nextLine();
                    index = 1;
                    for(Company company : companies) {
                        if (Integer.valueOf(choice).equals(index)) {
                            rentingCarScreen(company, customer);
                            run = false;
                            break;
                        }
                        index++;
                    }
                    if ("0".equals(choice)) {
                        run = false;
                    }
                }
            }else{
                System.out.println("\nThe company list is empty!");
            }
        }else {
            System.out.println("\nYou've already rented a car!");
        }

    }

    private void rentingCarScreen(Company company, Customer customer){
        int index = 1;
        int check = 0;
        boolean run = true;
        List<Car> cars = carDao.getAllCars(company);
        if(cars.size() > 0){
            for(Car car : cars) {
                if(!car.isRent()){
                    check++;
                }
            }
            if(check != 0){
                while (run){
                    System.out.println("\nChoose a car:");
                    for(Car car : cars) {
                        if(!car.isRent()){
                            System.out.println(index + ". " + car.getNAME());
                            index++;
                        }
                    }
                    System.out.println("0. Back");
                    String choice = scanner.nextLine();
                    index = 1;
                    for(Car car : cars){
                        if(!car.isRent()){
                            if(Integer.valueOf(choice).equals(index)){
                                System.out.println("\nYou rented '" + car.getNAME() + "'");
                                customerDao.rentCar(customer, car);
                                carDao.updateIsRateStatus(car, 1); // Rent = true (1)
                                car.setRent(true);
                                customer.setRENTED_CAR_ID(car.getID());
                                run = false;
                                break;
                            }
                            index++;
                        }
                    }
                    if("0".equals(choice)){
                        run = false;
                    }
                }
            }else {
                System.out.println("\nNo available cars in the '" + company.getNAME() + "' company");
            }
        }else {
            System.out.println("\nNo available cars in the '" + company.getNAME() + "' company");
        }
    }

    private void printRentCar(Customer customer){
        List<Company> companies = companyDao.getAllCompanies();
        if(customer.getRENTED_CAR_ID() == 0){
            System.out.println("\nYou didn't rent a car!");
        }else{
            for(Company company : companies) {
                List<Car> cars = carDao.getAllCars(company);
                for (Car car : cars) {
                    if (Objects.equals(car.getID(), customer.getRENTED_CAR_ID())) {
                        customer.setRENTED_CAR_ID(car.getID());
                        System.out.println("\nYour rented car:\n" + car.getNAME() + "\nCompany:\n" + company.getNAME());
                    }
                }
            }
        }
    }

    private void returnCar(Customer customer){
        List<Company> companies = companyDao.getAllCompanies();
        if(customer.getRENTED_CAR_ID() == 0){
            System.out.println("\nYou didn't rent a car!");
        }else{
            for(Company company : companies) {
                List<Car> cars = carDao.getAllCars(company);
                for (Car car : cars) {
                    if (Objects.equals(car.getID(), customer.getRENTED_CAR_ID())) {
                        customerDao.returnCar(customer);
                        carDao.updateIsRateStatus(car, 2); // Rent = false (2)
                        customer.setRENTED_CAR_ID(0);
                        car.setRent(false);
                        System.out.println("\nYou've returned a rented car!");
                    }
                }
            }
        }
    }
}
