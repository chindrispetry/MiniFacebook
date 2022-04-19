/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet;

import com.company.util.DBManagerDriver;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;


/**
 *
 * @author lamon
 */
@WebServlet("/Login")
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try{
            String email = request.getParameter("email");
            String passwordEncode = request.getParameter("password");
            String sqlSelectPassword = "SELECT id,password from Users WHERE email="+"'"+email+"'";
            DBManagerDriver dbmd = DBManagerDriver.getInstance();
            try(Connection con = dbmd.getConnection()){
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(sqlSelectPassword);
                if(rs.next()){
                    if(rs.getString("password").compareTo(passwordEncode)==0){
                        JSONObject obj = new JSONObject();
                        obj.put("page", "home.html");
                        Cookie cookie = new Cookie("id",String.valueOf(rs.getInt("id")));
                        cookie.setMaxAge(30*60);
                        response.addCookie(cookie);
                        response.getWriter().print(obj);
                    }
                    else{
                        response.getWriter().print(-1);
                    }
                }
                else{
                    response.getWriter().print(-1);
                }
            } 
            catch (SQLException ex) {
                Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        catch(Exception e){
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    }

