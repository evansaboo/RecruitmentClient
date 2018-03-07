/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 * DTO for competence 
 *
 * @author Oscar
 */
public class CompetenceDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long competenceNameId;
    private String name;
    private long competenceId;
    private String language;

    /**
     * Class Constructor
     */
    public CompetenceDTO() {
    }

    /**
     * Class Constructor
     *
     * @param competenceNameId sets the competenceNameId property
     * @param name sets the name property
     * @param competenceId sets the competenceId property
     * @param language sets the language property
     */
    public CompetenceDTO(Long competenceNameId, long competenceId, String name, String language) {
        this.competenceNameId = competenceNameId;
        this.name = name;
        this.competenceId = competenceId;
        this.language = language;
    }

    /**
     * Gets the value of the competenceNameId property
     *
     * @return competenceNameId as Long object
     */
    public Long getCompetenceNameId() {
        return competenceNameId;
    }

    /**
     * Sets the competenceNameId property
     *
     * @param competenceNameId the competenceNameId to set
     */
    public void setCompetenceNameId(Long competenceNameId) {
        this.competenceNameId = competenceNameId;
    }

    /**
     * Gets the value of the name property
     *
     * @return name as String object
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name property
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the value of the competenceId property
     *
     * @return competenceId as long
     */
    public long getCompetenceId() {
        return competenceId;
    }

    /**
     * Sets the competenceId property
     *
     * @param competenceId the competenceId to set
     */
    public void setCompetenceId(long competenceId) {
        this.competenceId = competenceId;
    }

    /**
     * Gets the value of the language property
     *
     * @return language as String object
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the language property
     *
     * @param language the language to set
     */
    public void setLanguage(String language) {
        this.language = language;
    }

}
