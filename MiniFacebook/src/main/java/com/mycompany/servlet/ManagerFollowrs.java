/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet;

import com.company.util.DBManagerDriver;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
public class ManagerFollowrs extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idSessionUser = request.getParameter("user");
        String idFollower = request.getParameter("follow");
        String absolutePath = "C:\\Users\\lamon\\OneDrive\\Documente\\NetBeansProjects\\Test\\src\\main\\webapp\\file\\";
        int urmariri  = removeOrAddUrmariri(absolutePath, idSessionUser, idFollower);
        
        JSONObject obj = new JSONObject();
        obj.put("follow",urmariri);
        response.getWriter().print(obj);
        
    }
    private int removeOrAddUrmariri(String absolutePath,String userID , String otherID) throws FileNotFoundException, IOException{
        BufferedReader reader = new BufferedReader(new FileReader(absolutePath + userID + "\\urmari.txt"));
        Map<String,String> mapUrmariri = new TreeMap<String, String>();
        int remove = 0;
        String str = "";
        while((str = reader.readLine())!= null && str.length()!= 0){
            String[] values = str.split(" ");
            mapUrmariri.put(values[0],values[1]);
        }
        if(mapUrmariri.containsKey(otherID)){
            mapUrmariri.remove(otherID);
        }else{
            mapUrmariri.put(otherID,LocalDate.now().toString());
            remove = 1;
        }
        
        int nr = 0;
        BufferedWriter writer = new BufferedWriter(new FileWriter(absolutePath + userID + "\\urmari.txt"));
        for(String s : mapUrmariri.keySet()){
            nr++;
            writer.write(s + " " + mapUrmariri.get(s));
            writer.newLine();
        }
        writer.close();
        
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
            String sqlID = "select iddetails from users where id=" + userID;
            int idDetails = 0;
            PreparedStatement statement = con.prepareStatement(sqlID);
            ResultSet set = statement.executeQuery();
            while(set.next()){
                idDetails = set.getInt("iddetails");
            }
            String sqlDetails = "Update details set friends=" + nr + "where iddetails="+idDetails;
            PreparedStatement stm = con.prepareStatement(sqlDetails);
            int result = stm.executeUpdate();
        }
        catch (SQLException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return remove;
    }
}
