/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import rest.RestCommunication;
import datarepresentation.Availability;
import datarepresentation.Competence;
import datarepresentation.CompetenceDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import model.LanguageChange;

/**
 *
 * @author Oscar
 */
@Named("applyManager")
@SessionScoped
public class ApplyManager implements Serializable {
    @Inject RestCommunication controller;
    
    @Inject private LanguageChange lc;    
    private List<CompetenceDTO> competences;
    private final HashMap<String, Long> competenceMapper = new HashMap<>();
    
    private List<Competence> comps = new ArrayList<>();
    private Competence comp = new Competence();
    private List<Availability> availabilities = new ArrayList<>();
    private Availability availability = new Availability();
    private final List<Double> yearsOfExp = new ArrayList<>();
    
    
    public void onPageLoad() {
        try {
            Response competencesResponse = controller.getCompetences(lc.getLanguage());
            competences = competencesResponse.readEntity(new GenericType<List<CompetenceDTO>>() {});
            
            competences.forEach((CompetenceDTO comp) -> {
                competenceMapper.put(comp.getName(), comp.getCompetenceId());
            });
            
            double interval = 0.25;
            for (double i = interval; i <= 75; i += interval) {
                yearsOfExp.add(i);
            }
        } catch(Exception ex) {/* exception with reading objects*/}
    }
    
    public void submitApplication() throws Exception {
        if(comps.isEmpty() && availabilities.isEmpty()) { return; }
        
        if(!comps.isEmpty()) {
            Response compResponse = controller.sendCompetences(comps);

            if(compResponse == null || !compResponse.getStatusInfo().equals(Response.Status.OK)) {
                /* TODO */
                System.out.println("COMPETENCE ERROR HANDLING");
                System.out.println("ERROR CODE = " + compResponse.getStatus() + ", REASON = " + compResponse.getStatusInfo().getReasonPhrase());
            }
        }
        
        if(!availabilities.isEmpty()) {
            Response availResponse = controller.sendAvailabilities(availabilities);

            if(availResponse == null || !availResponse.getStatusInfo().equals(Response.Status.OK)) {
                /* TODO */
                System.out.println("AVAILABILITY ERROR HANDLING");
                System.out.println("ERROR CODE = " + availResponse.getStatus() + ", REASON = " + availResponse.getStatusInfo().getReasonPhrase());
            }
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