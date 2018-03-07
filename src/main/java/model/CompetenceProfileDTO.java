/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * DTO for competence profile
 *
 * @author Evan
 */
public class CompetenceProfileDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long competenceId;
    private String competenceName;
    private double yearsOfExperience;
    private String language;

    /**
     * Class Constructor
     */
    public CompetenceProfileDTO() {
    }

    /**
     * Class Constructor
     *
     * @param competenceId sets the competenceId property
     * @param name sets the name property
     * @param yearsOfExperience sets the yearsOfExperience property
     * @param language
     */
    public CompetenceProfileDTO(Long competenceId, String name, double yearsOfExperience, String language) {
        this.competenceId = competenceId;
        this.competenceName = name;
        this.yearsOfExperience = yearsOfExperience;
        this.language = language;
    }

    /**
     * Gets the value of the getYearsOfExperience property
     *
     * @return getYearsOfExperience as double
     */
    public double getYearsOfExperience() {
        return yearsOfExperience;
    }

    /**
     * Sets the yearsOfExperience property
     *
     * @param yearsOfExperience the yearsOfExperience to set
     */
    public void setYearsOfExperience(double yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    /**
     * Gets the value of the competenceId property
     *
     * @return competenceId as Long object
     */
    public Long getCompetenceId() {
        return competenceId;
    }

    /**
     * Sets the competenceId property
     *
     * @param competenceId the competenceId to set
     */
    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
    }

    /**
     * Gets the value of the name property
     *
     * @return name as String object
     */
    public String getCompetenceName() {
        return competenceName;
    }
    
    /**
     * Sets the name property
     *
     * @param name the name to set
     */
    public void setCompetenceName(String name) {
        this.competenceName = name;
    }
    /**
     * Return the language of the competence
     * @return 
     */
    public String getLanguage() {
        return language;
    }
    /**
     * Return language of competence
     * @param language 
     */
    public void setLanguage(String language) {
        this.language = language;
    }
}