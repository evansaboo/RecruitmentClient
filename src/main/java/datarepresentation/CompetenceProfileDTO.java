/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datarepresentation;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author Oscar
 */
public class CompetenceProfileDTO implements Serializable {
    private Long competenceId;
    private transient String name;
    private int yearsOfExperience;
    private Date availableFrom;
    private Date availableTo;

    public CompetenceProfileDTO() {
    }

    public CompetenceProfileDTO(Long competenceId, String name, int yearsOfExperience, Date availableFrom, Date availableTo) {
        this.competenceId = competenceId;
        this.name = name;
        this.yearsOfExperience = yearsOfExperience;
        this.availableFrom = availableFrom;
        this.availableTo = availableTo;
    }

    public Long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(Long competenceId) {
        this.competenceId = competenceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public Date getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(Date availableFrom) {
        this.availableFrom = availableFrom;
    }

    public Date getAvailableTo() {
        return availableTo;
    }

    public void setAvailableTo(Date availableTo) {
        this.availableTo = availableTo;
    }
    
}