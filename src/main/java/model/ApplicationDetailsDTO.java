/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Evan
 */
public class ApplicationDetailsDTO implements Serializable {

    private String user_firstname;
    private String user_surname;
    private String user_email;
    private String user_ssn;

    private Date registrationDate;
    private List<StatusNameDTO> statusName;
    
    private List<CompetenceProfileDTO> competenceProfiles;
    private List<AvailabilityDTO> availabilities;
    
    public ApplicationDetailsDTO(){
        
    }
    public ApplicationDetailsDTO(
            String firstname,
            String surname,
            String email,
            String ssn,
            Date regDate,
            List<StatusNameDTO> statusName,
            List<CompetenceProfileDTO> cp,
            List<AvailabilityDTO> av) {

        this.user_firstname = firstname;
        this.user_surname = surname;
        this.user_email = email;
        this.user_ssn = ssn;
        this.registrationDate = regDate;
        this.statusName = statusName;
        this.competenceProfiles = cp;
        this.availabilities = av;
    }

    public String getUser_firstname() {
        return user_firstname;
    }

    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    public String getUser_surname() {
        return user_surname;
    }

    public void setUser_surname(String user_surrname) {
        this.user_surname = user_surrname;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_ssn() {
        return user_ssn;
    }

    public void setUser_ssn(String user_ssn) {
        this.user_ssn = user_ssn;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public List<StatusNameDTO> getStatusName() {
        return statusName;
    }

    public void setStatusName(List<StatusNameDTO> statusName) {
        this.statusName = statusName;
    }

    public List<AvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityDTO> availabilities) {
        this.availabilities = availabilities;
    }

    public List<CompetenceProfileDTO> getCompetenceProfiles() {
        return competenceProfiles;
    }

    public void setCompetenceProfiles(List<CompetenceProfileDTO> competenceProfiles) {
        this.competenceProfiles = competenceProfiles;
    }
    
}
