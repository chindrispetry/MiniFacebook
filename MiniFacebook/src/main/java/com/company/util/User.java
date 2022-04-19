/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.company.util;

/**
 *
 * @author lamon
 */
public class User {
    String firstName;
    String lastName;
    String email;
    String gen;
    String password;

    public User(String firstName, String lastName, String email, String gen, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gen = gen;
        this.password = password;
    }

    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "firstName=" + firstName + 
                ", lastName=" + lastName + 
                ", email=" + email + 
                ", gen=" + gen + 
                ", password=" + password + '}';
    }

    
    
}
