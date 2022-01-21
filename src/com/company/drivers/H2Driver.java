package com.company.drivers;

import com.company.Menu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class H2Driver {
    static final String JDBC_DRIVER = "org.h2.Driver";
    static final String DB_URL = "jdbc:h2:D:\\Mateusz\\Studia\\Technologie Obiektowe\\projekt\\car_sharing\\Car_sharing\\src\\com\\company\\db\\" + "carsharing";
    Connection conn = null;
    Statement stmt = null;

    public void connectionToH2DB() {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(true);

            Menu menu = new Menu();
            menu.activeMenu(conn);

        } catch (Exception se) {
            se.printStackTrace();
        }
    }

    public void closeConnectionToH2DB(){
        try{
            if(stmt!=null) stmt.close();
        }catch(SQLException ignored){}

        try{
            if(conn!=null) conn.close();
        }catch(SQLException se){
            se.printStackTrace();
        }
    }
}
