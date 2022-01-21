package com.company;

import com.company.drivers.H2Driver;

public class Main {

    public static void main(String[] args) {
        H2Driver h2Driver = new H2Driver();
        h2Driver.connectionToH2DB();
        h2Driver.closeConnectionToH2DB();
    }
}