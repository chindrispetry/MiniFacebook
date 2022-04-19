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

/**
 *
 * @author lamon
 */
public class Details extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try(Connection con = DBManagerDriver.getInstance().getConnection())
        {
            con.setAutoCommit(true);
            String location = request.getParameter("location");
            String sqlLocation = "Select idcity from city where numecity='" + location+"'";
            int idcity = 0;
            PreparedStatement stm = con.prepareStatement(sqlLocation);
            ResultSet set = stm.executeQuery();
            while(set.next()){
                idcity = set.getInt("idcity");
            }
            String id = request.getParameter("id");
            int IDDETAILS = 0;
            String sqlUsers = "select iddetails from users where id="+id;
            PreparedStatement st = con.prepareStatement(sqlUsers);
            ResultSet resultSet = st.executeQuery();
            while(resultSet.next()){
                IDDETAILS = resultSet.getInt("iddetails");
            }
            String updateDetails = "UPDATE Details SET bestd="+"'"+request.getParameter("favday")+"'"
                    + "," + "bestf=" + "'" + request.getParameter("favfood")+"'"
                    + "," + "bestm = " + "'" + request.getParameter("favmusic") + "'" 
                    +"," + "dateofbirth="  + "'" + request.getParameter("dateofbirth") + "'"
                    +"," + "studies="  + "'" + request.getParameter("studies") + "'"
                    +"," + "idcity= "  + idcity  +" WHERE iddetails = " + IDDETAILS;
            PreparedStatement stmt = con.prepareStatement(updateDetails);
            int result = stmt.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Details.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Details.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
