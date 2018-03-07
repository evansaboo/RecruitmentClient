/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DTO for application information
 *
 * @author Evan
 */
public class ApplicationDetailsDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String user_firstname;
    private String user_surname;
    private String user_email;
    private String user_ssn;

    private Date registrationDate;
  
    private List<StatusNameDTO> statusName;
    
    private List<CompetenceProfileDTO> competenceProfiles;
    private List<AvailabilityDTO> availabilities;


    LanguageChange lc = new LanguageChange();
   
     /**
     * Constructor
     */
    public ApplicationDetailsDTO(){
        
    }

     /**
     * Creates DTO of application
     *
     * @param firstname sets first name property
     * @param surname sets surname property
     * @param email sets email property
     * @param ssn sets social security number property
     * @param regDate sets date property
     * @param statusName sets status of application
     * @param cp sets competence of application
     * @param av sets availability of application
     */
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
        this.registrationDate = new Date(regDate.getTime());
        this.statusName = statusName;
        this.competenceProfiles = cp;
        this.availabilities = av;
    }

    /**
     * Returns the applicants first name
     *
     * @return first name of applicant
     */
    public String getUser_firstname() {
        return user_firstname;
    }

    /**
     * Set the first name of the applicant
     *
     * @param user_firstname entered first name
     */
    public void setUser_firstname(String user_firstname) {
        this.user_firstname = user_firstname;
    }

    /**
     * Returns the applicants last name
     *
     * @return surname of applicant
     */
    public String getUser_surname() {
        return user_surname;
    }

    /**
     * Set the surname of the applicant
     *
     * @param user_surrname entered surname
     */
    public void setUser_surname(String user_surrname) {
        this.user_surname = user_surrname;
    }

    /**
     * Returns the applicants email address
     *
     * @return email address of applicant
     */
    public String getUser_email() {
        return user_email;
    }

    /**
     * Set the email address of the applicant
     *
     * @param user_email entered email
     */
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    /**
     * Returns the applicants social security number
     *
     * @return social security number of applicant
     */
    public String getUser_ssn() {
        return user_ssn;
    }

    /**
     * Set the social security number of the applicant
     *
     * @param user_ssn entered social security number
     */
    public void setUser_ssn(String user_ssn) {
        this.user_ssn = user_ssn;
    }

    /**
     * Returns the applicants social security number
     *
     * @return social security number of applicant
     */
    public Date getRegistrationDate() {
        return lc.parseDateAfterLocale(registrationDate);
    }

    /**
     * Set the registration date of the application
     *
     * @param registrationDate entered social security number
     */
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = lc.parseDateAfterLocale(registrationDate);
    }

      /**
     * Returns the status of the application
     *
     * @return application status
     */
    public List<StatusNameDTO> getStatusName() {
        return statusName;
    }
  
    /**
     * Set the status of the application
     *
     * @param statusName status of the application
     */
    public void setStatusName(List<StatusNameDTO> statusName) {
        this.statusName = statusName;
    }

    /**
     * Returns the availabilities of the applicant
     *
     * @return applicants availabilities
     */
    public List<AvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    /**
     * Set the availabilities of the applicant
     *
     * @param availabilities applicants availabilities
     */
    public void setAvailabilities(List<AvailabilityDTO> availabilities) {
        this.availabilities = availabilities;
    }

    /**
     * Returns the competences of the applicant
     *
     * @return applicants competences
     */
    public List<CompetenceProfileDTO> getCompetenceProfiles() {
        return competenceProfiles;
    }

    /**
     * Sets the competences of the applicant
     *
     * @param competenceProfiles list of applicants competences
     */
    public void setCompetenceProfiles(List<CompetenceProfileDTO> competenceProfiles) {
        this.competenceProfiles = competenceProfiles;
    }

}
