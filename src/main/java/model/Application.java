/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Evan
 */
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;
    private long applicationId;
    private String firstname;
    private String surname;
    private String email;

    
    public Application(long applicationId, String firstname, String surname, String email){
        this.applicationId = applicationId;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
