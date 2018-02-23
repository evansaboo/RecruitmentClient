/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Evan
 */
@Named("viewApplication")
@SessionScoped
public class ViewApplication implements Serializable{
    private String firstname;
    private String surrname;
    private String email;
    private String ssn;
    
  
    private long applicationId;
    private Date registrationDate;
    private final ArrayList<String> competenceProfiles = new ArrayList<>();
    
    private void initPage(){
        
    }
    
    public long getApplicationId() {
        return applicationId;
    }
    
    public void setApplicationId(long applicationId){
        this.applicationId = applicationId;
    }
    
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurrname() {
        return surrname;
    }

    public void setSurrname(String surrname) {
        this.surrname = surrname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }
    
    
}