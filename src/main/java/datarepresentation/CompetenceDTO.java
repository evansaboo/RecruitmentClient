/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datarepresentation;

import java.io.Serializable;

/**
 *
 * @author Oscar
 */
public class CompetenceDTO{

    private Long competenceNameId;
    private String name;
    private long competenceId;
    private String language;
    
    public CompetenceDTO() {
    }

    public CompetenceDTO(Long competenceNameId,long competenceId, String name, String language) {
        this.competenceNameId = competenceNameId;
        this.name = name;
        this.competenceId = competenceId;
        this.language = language;
    }



    public Long getCompetenceNameId() {
        return competenceNameId;
    }

    public void setCompetenceNameId(Long competenceNameId) {
        this.competenceNameId = competenceNameId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCompetenceId() {
        return competenceId;
    }

    public void setCompetenceId(long competenceId) {
        this.competenceId = competenceId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
