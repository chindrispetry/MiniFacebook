/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet;

import com.company.util.DBManagerDriver;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 *
 * @author lamon
 */
public class Delete extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response){
        String id = request.getParameter("idpostare");
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
            con.setAutoCommit(true);
            String sqlSelectFive = "delete from postari where idpostare="+id;
            PreparedStatement preparedStatement = con.prepareStatement(sqlSelectFive);
            int rs = preparedStatement.executeUpdate();
            
    }   catch (SQLException ex) {
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
