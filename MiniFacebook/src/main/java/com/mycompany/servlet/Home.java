/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.servlet;

import com.company.util.DBManagerDriver;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Home extends HttpServlet {

   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
            String idUser = request.getParameter("id");
            String absolutePath = "C:\\Users\\lamon\\OneDrive\\Documente\\NetBeansProjects\\Test\\src\\main\\webapp\\file\\";
            BufferedReader reader = new BufferedReader(new FileReader(absolutePath + idUser + "\\urmari.txt"));
            String str = "";
            Map<String,String> map = new TreeMap<String,String>();
            
            while((str = reader.readLine()) != null && str.length() !=0){
                String[] values = str.split(" ");
                map.put(values[0], values[1]);
            }
            JSONObject obj = new JSONObject();
            int i = 0;
            int p = 0;
            int k = 0;
            String keySet = "";
            for(String s : map.keySet()){
                keySet += s + ",";
                String sqlPos = "Select *from postari where iduser="+s + " and datepost >=" + "'"+ map.get(s)+"'";
                PreparedStatement stmt = con.prepareStatement(sqlPos);
                ResultSet result = stmt.executeQuery();
                while(result.next()){
                    JSONObject post = new JSONObject();
                    post.put("idpostare", result.getString("idpostare"));
                    post.put("iduser",result.getInt("iduser"));
                    post.put("imageurl", result.getString("imageurl"));
                    post.put("description", result.getString("description"));
                    post.put("datepost", result.getString("datepost"));
                    obj.put("post" + i++ , post);
                }
                String sqlUser = "Select id,firstname,lastname,urlprofil from users where id=" + s;
                PreparedStatement statement = con.prepareStatement(sqlUser);
                ResultSet set = statement.executeQuery();
                while(set.next()){
                    JSONObject user = new JSONObject();
                    user.put("id", set.getInt("id"));
                    user.put("firstname", set.getString("firstname"));
                    user.put("lastname", set.getString("lastname"));
                    user.put("urlprofil", set.getString("urlprofil"));
                    obj.put("user" + p++, user);
                }
            }
            String sqlSelectFive;
            String sirSet = map.keySet().toString();
            sirSet = sirSet.substring(1, sirSet.length()-1);
            if(map.keySet().isEmpty()){
                    sqlSelectFive = "select id,firstname,lastname,urlprofil,d.dateofbirth,c.numecity from users u ,details d,city c"
                    + " where d.iddetails = u.iddetails and c.idcity=d.idcity and rownum <= 8 and id!=" + idUser;
            }else{
                sqlSelectFive = "select id,firstname,lastname,urlprofil,d.dateofbirth,c.numecity from users u ,details d,city c"
                    + " where id not in ("+sirSet + ") and d.iddetails = u.iddetails and c.idcity=d.idcity and rownum <= 5 and id!="+idUser;
            }
            PreparedStatement preparedStatement = con.prepareStatement(sqlSelectFive);
            ResultSet rs = preparedStatement.executeQuery();
            JSONObject recomd = new JSONObject();
            int j = 0;
            while(rs.next()){
                JSONObject json = new JSONObject();
                json.put("id", rs.getInt("id"));
                json.put("firstname", rs.getString("firstname"));
                json.put("lastname", rs.getString("lastname"));
                json.put("urlprofil", rs.getString("urlprofil"));
                json.put("dateofbirth", rs.getString("dateofbirth"));
                json.put("numecity", rs.getString("numecity"));
                recomd.put("recomd" + j++,json);
            }
            obj.put("recomandari", recomd);
            response.getWriter().print(obj);
        } catch (SQLException ex) {
           Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Home.class.getName()).log(Level.SEVERE, null, ex);
       }
    }


}
