/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.util;

import java.time.LocalDate;

/**
 *
 * @author lamon
 */
public class Postare {
    private int ID;
    private String date;
    private String description;
    private String imageURL;
    private int nrLike;
    private String numeFile;

    public Postare(int ID, String date, String description, String imageURL, int nrLike, String numeFile) {
        this.ID = ID;
        this.date = date;
        this.description = description;
        this.imageURL = imageURL;
        this.nrLike = nrLike;
        this.numeFile = numeFile;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getNrLike() {
        return nrLike;
    }

    public void setNrLike(int nrLike) {
        this.nrLike = nrLike;
    }

    public String getNumeFile() {
        return numeFile;
    }

    public void setNumeFile(String numeFile) {
        this.numeFile = numeFile;
    }

    @Override
    public String toString() {
        return "Postare{" + "ID=" + ID + ","+", date=" + date + ", description=" + description +
                ", imageURL=" + imageURL + ", nrLike=" + nrLike + ", numeFile=" + numeFile + '}';
    }

    
    
}
