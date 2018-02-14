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
public class CompetenceDTO implements Serializable{
    private Long competenceId;
    private String name;

    public CompetenceDTO() {
    }

    public CompetenceDTO(Long competenceId, String name) {
        this.competenceId = competenceId;
        this.name = name;
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
    
}
