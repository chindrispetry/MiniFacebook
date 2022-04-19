/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet;

import com.company.util.DBManagerDriver;
import com.company.util.Utills;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author lamon
 */
public class Search extends HttpServlet {

   



    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
            con.setAutoCommit(true);
            String searcString = request.getParameter("search");
            String sqlSelectFive = "select id,firstname,lastname,urlprofil,iddetails from users"
                        + " where lastname like '%"+searcString + "%' or firstname like '%" + searcString+ "%'";
            PreparedStatement preparedStatement = con.prepareStatement(sqlSelectFive);
            ResultSet rs = preparedStatement.executeQuery();
            JSONObject search = new JSONObject();
            int j = 0;
            while(rs.next()){
                JSONObject json = new JSONObject();
                json.put("id", rs.getInt("id"));
                json.put("firstname", rs.getString("firstname"));
                json.put("lastname", rs.getString("lastname"));
                json.put("urlprofil", rs.getString("urlprofil"));
                String sqlSelect = "select dateofbirth,studies,idcity from details where "
                        + "iddetails  = " +rs.getInt("iddetails");
                PreparedStatement preparedStatement1 = con.prepareStatement(sqlSelect);
                ResultSet rs1 = preparedStatement1.executeQuery();
                int idcity = 0;
                while(rs1.next()){
                    json.put("dateofbirth", rs1.getString("dateofbirth"));
                    json.put("studies", rs1.getString("studies"));
                    idcity = rs1.getInt("idcity");
                }
                String sqlCity = "select numecity from city where idcity=" +idcity;
                PreparedStatement statement = con.prepareStatement(sqlCity);
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    json.put("numecity", set.getString("numecity"));
                }
                search.put("search" + j++,json);
            }
            response.getWriter().print(search);
        } catch (SQLException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }catch (Exception ex) {
            Logger.getLogger(Search.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
