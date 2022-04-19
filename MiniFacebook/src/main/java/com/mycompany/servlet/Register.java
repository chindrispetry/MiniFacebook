/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet;

import com.company.util.DBManagerDriver;
import com.company.util.User;
import com.company.util.Utills;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @author lamon
 */
public class Register extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       try{
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String email = request.getParameter("email");
            String gen = request.getParameter("gen");
            String Password = request.getParameter("password");
            PrintWriter out = response.getWriter();
            User user = Utills.validationUser(new User(firstName,lastName,email,gen,Password));
            
            if(user != null){
                 
                 DBManagerDriver dbmd = DBManagerDriver.getInstance();
                 try (Connection con = dbmd.getConnection();)
                  {
                      con.setAutoCommit(true);
                      String sqlSelectEmail = "SELECT * from Users WHERE email= "+"'"+email+"'";
                      
                      PreparedStatement stmt = con.prepareStatement(sqlSelectEmail);
                      ResultSet rs = stmt.executeQuery();
                      if(rs.next()){
                          out.print(-1);
                      }else{
                          Random random = new Random();
                          int idcity = random.nextInt(7);
                          while(idcity == 2){
                              idcity = random.nextInt(7);
                          }
                          String sqlDetails;
                          sqlDetails = "Insert into details values (iddetails.nextval," + "'" + "none" + "'"  + ","
                                  + "'" + "none"  + "'" +","+ "'" + "none" + "'" +"," + 0 +","+idcity+"," + 
                                  "'" + "01.01.1999" +"'" +","+ "'" + "none" +"'"+")";
                          PreparedStatement stm = con.prepareStatement(sqlDetails);
                          int set = stm.executeUpdate();
                          
                          int idMax = 0;
                          String sqlId = "select max(iddetails) from Details";
                          PreparedStatement st = con.prepareStatement(sqlId);
                          ResultSet result = st.executeQuery();
                          while(result.next()){
                              idMax = result.getInt("max(iddetails)");
                          }
                          String sqlInsertUser = "INSERT INTO Users  VALUES ("+"id.nextval"+","+
                              "'" + user.getFirstName()+"'" +"," + 
                              "'" + user.getLastName()+"'"+"," + 
                              "'" + user.getEmail() + "'" +"," + 
                              Utills.getGenToKey(user.getGen()) + "," + 
                              "'" + user.getPassword() + "'"  + "," + 
                              idMax + ","+"'" + "none" + "'" +","+"'" + "none" + "'"+")";
                          stmt = con.prepareStatement(sqlInsertUser);
                          
                          //creez director cu id-ul user-ului
                          int resultSet = stmt.executeUpdate();
                          String lastID = "Select max(id) from users";
                          int id = 0;
                          PreparedStatement preparedStatement = con.prepareStatement(lastID);
                          ResultSet resultSet1 = preparedStatement.executeQuery();
                          while(resultSet1.next()){
                             id = resultSet1.getInt("max(id)");
                          }
                          String path = "C:\\Users\\lamon\\OneDrive\\Documente\\NetBeansProjects\\Test\\src\\main\\webapp\\file\\" + id;
                          File file = new File(path);
                          file.mkdir();
                          
                          //creez fisiere urmaritori.txt,urmariti.txt,AbonatPost.txt
                          File  urmariri = new File(path+"\\urmari.txt");
                          urmariri.createNewFile();
                          out.print(1);
                      }
                 }

                 catch (SQLException ex) {
                     Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
                     out.print(ex);
                 }

                }
                else{
                    out.println("User invalid fields");
                 }
          }
          catch(Exception e){
              Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, e);
          }
        }
       }