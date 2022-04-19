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
import java.sql.ResultSetMetaData;
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
public class Profil extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        JSONObject profil = usersQuery(request, response);
        int id =  (int) profil.get("iddetails");
        JSONObject details = details = detailsQuery(request,response,id);
        JSONObject location = locationQuery((int)details.get("idcity"));
        JSONObject postari = postariQuery(Integer.parseInt(request.getParameter("id")));
        JSONObject friends = new JSONObject();
        try {
            friends = friendQuery(request);
        } catch (SQLException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        JSONObject user = new JSONObject();
        user.put("profil", profil);
        user.put("details", details);
        user.put("location", location);
        user.put("postari", postari);
        user.put("friends", friends);
        response.getWriter().print(user);
    }
    private JSONObject usersQuery(HttpServletRequest request, HttpServletResponse response){
        JSONObject profil = new JSONObject();
        
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
            String ID = request.getParameter("id");
            String sqlSelect = "SELECT firstname,lastname,urlprofil,urlcopert,iddetails from Users "
                    + "where id="+ID;
            con.setAutoCommit(true);
            
            PreparedStatement stmt = con.prepareStatement(sqlSelect);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                profil.put("firstname", rs.getString("firstname"));
                profil.put("lastname", rs.getString("lastname"));
                profil.put("urlprofil", rs.getString("urlprofil"));
                profil.put("urlcopert", rs.getString("urlcopert"));
                profil.put("iddetails", rs.getInt("iddetails"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return profil;
    }
    private JSONObject friendQuery(HttpServletRequest request) throws IOException, SQLException, SQLException, ClassNotFoundException{
        String idUser = request.getParameter("id");
        String absolutePath = "C:\\Users\\lamon\\OneDrive\\Documente\\NetBeansProjects\\Test\\src\\main\\webapp\\file\\";
        BufferedReader reader = new BufferedReader(new FileReader(absolutePath + idUser + "\\urmari.txt"));
        String str = "";
        Map<String,String> map = new TreeMap<String,String>();
            
        while((str = reader.readLine()) != null && str.length() !=0){
            String[] values = str.split(" ");
            map.put(values[0], values[1]);
        }
        JSONObject recomd = new JSONObject();
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
            con.setAutoCommit(true);
            String sirSet = map.keySet().toString();
            sirSet = sirSet.substring(1, sirSet.length()-1);
            String sqlSelectFive = "select id,firstname,lastname,urlprofil,d.dateofbirth,c.numecity from users u ,details d,city c"
                        + " where id in ("+sirSet + ") and d.iddetails = u.iddetails and c.idcity=d.idcity";
            PreparedStatement preparedStatement = con.prepareStatement(sqlSelectFive);
            ResultSet rs = preparedStatement.executeQuery();
            
            int j = 0;
            while(rs.next()){
                JSONObject json = new JSONObject();
                json.put("id", rs.getInt("id"));
                json.put("firstname", rs.getString("firstname"));
                json.put("lastname", rs.getString("lastname"));
                json.put("urlprofil", rs.getString("urlprofil"));
                json.put("dateofbirth", rs.getString("dateofbirth"));
                json.put("numecity", rs.getString("numecity"));
                recomd.put("follow" + j++,json);
            }
        }
        return recomd;
    }
    private JSONObject detailsQuery(HttpServletRequest request, HttpServletResponse response,int idDetails){
        JSONObject details = new JSONObject();
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
            String sqlDetails = "SELECT *from Details where iddetails=" + idDetails;
            PreparedStatement stm = con.prepareStatement(sqlDetails);
            ResultSet result = stm.executeQuery();

            while(result.next()){
                details.put("bestf", result.getString("bestf"));
                details.put("bestm", result.getString("bestm"));
                details.put("bestd", result.getString("bestd"));
                details.put("friends", result.getString("friends"));
                details.put("dateofbirth", result.getString("dateofbirth"));
                details.put("studies", result.getString("studies"));
                details.put("idcity",result.getInt("idcity"));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return details;
    }
    private JSONObject locationQuery(int idCity){
        JSONObject location = new JSONObject();
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
        String sqlSelect = "Select r.denumire,coun.numecountry,c.numecity from"
                    + " region r,country coun,city c WHERE"
                    + " c.idcountry = coun.idcountry  and coun.idregion = r.id and idcity=" + idCity;
            PreparedStatement Stmt = con.prepareStatement(sqlSelect);
            ResultSet resultSet = Stmt.executeQuery();
            while(resultSet.next()){
                location.put("Region", resultSet.getString("denumire"));
                location.put("Country", resultSet.getString("numecountry"));
                location.put("City", resultSet.getString("numecity"));
            }

        }
        catch (SQLException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return location;
    }
    private JSONObject postariQuery(int idUser){
        JSONObject postari = new JSONObject();
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
            String sqlSelect = "Select * from postari where iduser=" +idUser;
            String p = "post";
            int i = 0;
            PreparedStatement Stmt = con.prepareStatement(sqlSelect);
            ResultSet resultSet = Stmt.executeQuery();
            while(resultSet.next()){
                JSONObject post = new JSONObject();
                post.put("idpostare", resultSet.getString("idpostare"));
                post.put("iduser",idUser);
                post.put("imageurl", resultSet.getString("imageurl"));
                post.put("description", resultSet.getString("description"));
                post.put("datepost", resultSet.getString("datepost"));
                postari.put(p + i++ , post);
            }

        }
        catch (SQLException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Profil.class.getName()).log(Level.SEVERE, null, ex);
        }
        return postari;
    }
}
