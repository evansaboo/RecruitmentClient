/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * Creates an application
 * @author Email
 */
public class Application implements Serializable {
    private static final long serialVersionUID = 1L;
    private long applicationId;
    private String firstname;
    private String surname;
    private String email;

    /**
     * Creates an application for a user
     * @param applicationId The id for the application
     * @param firstname First name of applicant
     * @param surname   Last name of applicant
     * @param email     Email address of applicant
     */
    public Application(long applicationId, String firstname, String surname, String email){
        this.applicationId = applicationId;
        this.firstname = firstname;
        this.surname = surname;
        this.email = email;
    }
    /**
     * Rereutns the applications id
     * @return id of application
     */
    public long getApplicationId() {
        return applicationId;
    }
    /**
     * Set the autoincremented value from database for the application
     * @param applicationId autoincremented value from database
     */
    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }
    /**
     * Returns the applicants first name
     * @return first name of applicant
     */
    public String getFirstname() {
        return firstname;
    }
    /**
     * Set the first name of the applicant
     * @param firstname entered first name
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    /**
     * Returns the applicants last name
     * @return surname of applicant
     */
    public String getSurname() {
        return surname;
    }
    /**
     * Set the surname of the applicant
     * @param surname entered surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }
    /**
     * Returns the applicants email address
     * @return email address of applicant
     */
    public String getEmail() {
        return email;
    }
     /**
     * Set the email address of the applicant
     * @param email entered email
     */
    public void setEmail(String email) {
        this.email = email;
    }
}
