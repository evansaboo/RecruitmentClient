/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import datarepresentation.Availability;
import datarepresentation.Competence;
import datarepresentation.CompetenceDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Oscar
 */
@Named("applyManager")
@ManagedBean
@ViewScoped
public class ApplyManager implements Serializable {
    private final String BASE_URL = "http://localhost:8080/RecruitmentServ/webresources/apply";
    private final String COMPETENCE_PATH = "competence";
    private final String AVAILABILITY_PATH = "availability";
    
    private List<CompetenceDTO> competences;
    private HashMap<String, Long> competenceMapper = new HashMap<>();
    
    private List<Competence> comps = new ArrayList<>();
    private Competence comp = new Competence();
    private List<Availability> availabilities = new ArrayList<>();
    private Availability availability = new Availability();
    
    private final Client client = ClientBuilder.newClient();
    private final List<Double> yearsOfExp = new ArrayList<>();
    
    @PostConstruct
    private void populateCompetences() {
        competences = client.target(BASE_URL)
                .request().get(new GenericType<List<CompetenceDTO>> () {});
        
        competences.forEach((CompetenceDTO comp) -> {
            competenceMapper.put(comp.getName(), comp.getCompetenceId());
        });
        
        for(double i = 0; i < 75; i += 0.25) {
            yearsOfExp.add(i);
        }
    }

    public void submitApplication() {
        if(comps.isEmpty() && availabilities.isEmpty()) { return; }
        
        Response compResponse = null;
        if(!comps.isEmpty()) {
            GenericEntity<List<Competence>> entity = new GenericEntity<List<Competence>>(comps) {};
            compResponse = client.target(BASE_URL).path(COMPETENCE_PATH)
                    .request().post(Entity.json(entity));
        }
        
        Response availResponse = null;
        if(!availabilities.isEmpty()) {
            GenericEntity<List<Availability>> entity = new GenericEntity<List<Availability>>(availabilities) {};
            availResponse = client.target(BASE_URL).path(AVAILABILITY_PATH)
                    .request().post(Entity.json(entity));
        }
        
        // check responses
        if(compResponse == null || !compResponse.getStatusInfo().equals(Response.Status.OK)) {
            System.out.println("COMPETENCE ERROR HANDLING");
        }
        
        if(availResponse == null || !availResponse.getStatusInfo().equals(Response.Status.OK)) {
            System.out.println("AVAILABILITY ERROR HANDLING");
        }

        comps = new ArrayList<>();
        availabilities = new ArrayList<>();
    }
    
    public void addCompetence() {
        if(comp.getName() == null) { return; }
        
        Long compId = competenceMapper.get(comp.getName());
        comp.setCompetenceId(compId);
        comps.add(comp);
        comp = new Competence();
    }
    
    public void addAvailability() {
        if(availability.getToDate() == null || availability.getFromDate() == null) { return; }
        availabilities.add(availability);
        availability = new Availability();
    }
    
    public void deleteEntry(Object entry) {
        if(entry instanceof Availability) {
            availabilities.remove(Availability.class.cast(entry));
        } else if(entry instanceof Competence) {
            comps.remove(Competence.class.cast(entry));
        } else {
            // log
        }
    }

    public List<Competence> getComps() {
        return comps;
    }

    public void setComps(List<Competence> comps) {
        this.comps = comps;
    }

    public Competence getComp() {
        return comp;
    }

    public void setComp(Competence comp) {
        this.comp = comp;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<Availability> availabilities) {
        this.availabilities = availabilities;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public List<Double> getYearsOfExp() {
        return yearsOfExp;
    }

    
    public List<CompetenceDTO> getCompetences() {
        return competences;
    }

    public void setCompetences(List<CompetenceDTO> competences) {
        this.competences = competences;
    }

}