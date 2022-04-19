/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author lamon
 */
public class DBManagerDriver {
    private static DBManagerDriver managerDriver = new DBManagerDriver();
    private Connection connection;
    
    private DBManagerDriver() {
     
    }
    public Connection getConnection()throws SQLException,ClassNotFoundException{
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",
                "system", "root");
        return  this.connection;
    
    }
    public static DBManagerDriver getInstance(){
            return managerDriver;
    }

}
