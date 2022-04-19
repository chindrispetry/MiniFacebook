/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet;

import com.company.util.DBManagerDriver;
import com.company.util.User;
import com.company.util.Utills;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.simple.JSONObject;
import sun.jvm.hotspot.utilities.soql.SOQLException;

/**
 *
 * @author lamon
 */
public class Friends extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try(Connection con  = DBManagerDriver.getInstance().getConnection()){
            String sql = "SELECT id,firstname,lastname,email,g.name,password from Users u, Gen g "
                    + "WHERE id = " + request.getParameter("id") + " and u.idgen = g.idgen";
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet set = stmt.executeQuery();
            while(set.next()){
                User user = new User(set.getString("firstname"),set.getString("lastname"),
                        set.getString("email"),set.getString("name"),set.getString("password"));
                if(user!=null){
                    List<String> denumire = List.of("firstName","lastName","email","gen");
                    List<String> value = List.of(user.getFirstName(),user.getLastName(),user.getEmail(),user.getGen());
                    JSONObject obj = Utills.parseToJSON(denumire, value);
                    response.getWriter().print(obj);
                }
            }
            
        }
        catch(Exception e){
        
        }
    }

}
