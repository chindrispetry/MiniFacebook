/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.util;

import java.util.List;
import org.json.simple.JSONObject;

/**
 *
 * @author lamon
 */
public class Utills {
    public static User validationUser(User u){
        if(u.getFirstName().length() < 3)
            return null;
        if(u.getLastName().length() < 3)
            return null;
        if(u.getEmail().length() < 10)
            return null;
        if(u.getGen().length() == 0)
            return null;
        if(u.getPassword().length()  <6)
            return null;
        return u;
    }
    public static int getGenToKey(String gen){
        if(gen.compareTo("Male") == 0)
           return 0;
        if(gen.compareTo("Female") == 0)
            return 1;
        return 2;
    }
    public static JSONObject parseToJSON(List<String> denumire,List<String> value){
        JSONObject obj = new JSONObject();
        boolean validParse = denumire.size() == value.size();
        if(validParse){
            int n = denumire.size();
            for(int i = 0;i<n;i++){
                obj.put(denumire.get(i), value.get(i));
            }
        }
        return obj;
    }
}
