/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.Upload;

import com.company.util.DBManagerDriver;
import com.company.util.Postare;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.fileupload.FileItem;


import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author lamon
 */
@MultipartConfig(
  fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
  maxFileSize = 1024 * 1024 * 10,      // 10 MB
  maxRequestSize = 1024 * 1024 * 100   // 100 MB
)
public class UploadImage extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String absolutePath = "C:\\Users\\lamon\\OneDrive\\Documente\\NetBeansProjects\\Test\\src\\main\\webapp\\";
        String url = "file\\";
        Part post = request.getPart("post");
        for (Part part : request.getParts()) {
          post.write(absolutePath + url + "input.txt");
        }
        BufferedReader reader = new BufferedReader(new FileReader(absolutePath + url+ "input.txt"));
        String str = reader.readLine();
        
        JSONParser obj = new JSONParser();
        JSONObject  json = new JSONObject();
        try {
            json = (JSONObject)obj.parse(str);
        } catch (ParseException ex) {
            Logger.getLogger(UploadImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        int idMax = 0;
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
            String sqlId = "select max(idpostare) from postari";
            PreparedStatement st = con.prepareStatement(sqlId);
            ResultSet resultSet = st.executeQuery();
            while(resultSet.next()){
                  idMax = resultSet.getInt("max(idpostare)");
            }
        } catch (SQLException ex) {
            Logger.getLogger(UploadImage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UploadImage.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int id = Integer.parseInt((String)json.get("id"));
        String date = (String)json.get("date");
        String description = (String)json.get("description");
        String nameFile = (String)json.get("name") + idMax + ".png";
        String urlProfil = url + "\\" + (String)json.get("id") + "\\" + nameFile;
        int nrLike = 0;
        
        Postare postare = new Postare(id,date,description,urlProfil,nrLike,nameFile);
       
        Part filePart = request.getPart("photo");
        String url1;
        url1 = absolutePath  +  url + String.valueOf(postare.getID()) + "\\" +postare.getNumeFile();
        for (Part part : request.getParts()) {
          part.write(url1);
        }
        
        try(Connection con = DBManagerDriver.getInstance().getConnection()){
            con.setAutoCommit(true);
            boolean profilPicture = postare.getNumeFile().contains("profil");
            boolean copertPicture = postare.getNumeFile().contains("copert");
            if(profilPicture || copertPicture){
                String s = "urlprofil";
                if(copertPicture){
                    s = "urlcopert";
                }
                String sqlUpdate  = "update users set " + s + "="+ "'"+postare.getImageURL()+"'" + " where id=" + postare.getID();
                PreparedStatement stmt = con.prepareStatement(sqlUpdate);
                int rs = stmt.executeUpdate();
            }
            
            String sqlPostare = "INSERT INTO postari values (idpostare.nextval,"+
                    postare.getID() + "," + "'"+postare.getImageURL()+"'" + ","+
                    nrLike + "," + "'"+ postare.getDescription() + "'" + "," + "'"+
                    LocalDate.now().toString() +"')";
            PreparedStatement stm = con.prepareStatement(sqlPostare);
            int result = stm.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(UploadImage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UploadImage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
