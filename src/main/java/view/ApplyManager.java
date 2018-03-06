/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import rest.RestCommunication;
import datarepresentation.AvailabilityDTO;
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
    @Inject private RestCommunication controller;
    @Inject private LanguageChange lc;
    
    private List<CompetenceDTO> competences;
    private final HashMap<String, Long> competenceMapper = new HashMap<>();
    
    private List<Competence> comps = new ArrayList<>();
    private Competence comp = new Competence();
    private List<AvailabilityDTO> availabilities = new ArrayList<>();
    private AvailabilityDTO availability = new AvailabilityDTO();
    private final List<Double> yearsOfExp = new ArrayList<>();
    
    private String msgToUser;
    
    public void onPageLoad() {
        try {
            Response competencesResponse = controller.getCompetences();
            competences = competencesResponse.readEntity(new GenericType<List<CompetenceDTO>>() {});
            competences.forEach((CompetenceDTO comp) -> {
                competenceMapper.put(comp.getName(), comp.getCompetenceId());
            });
            
            double interval = 0.25;
            for (double i = interval; i <= 75; i += interval) {
                yearsOfExp.add(i);
            }
        } catch(Exception ex) {/* exception with reading objects*/
            System.out.println("Error fetching stuff: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void submitApplication() throws Exception {
        if(comps.isEmpty() && availabilities.isEmpty()) { return; }
        
        Response availResponse = Response.notModified().build();
        Response compResponse = Response.notModified().build();
        
        if(!comps.isEmpty()) {
            compResponse = controller.sendCompetences(comps);
            if(compResponse == null || !compResponse.getStatusInfo().equals(Response.Status.OK)) {
                /* TODO */
                System.out.println("COMPETENCE ERROR HANDLING");
                System.out.println("ERROR CODE = " + compResponse.getStatus() + ", REASON = " + compResponse.getStatusInfo().getReasonPhrase());
            }
        }
        
        if(!availabilities.isEmpty()) {
            availResponse = controller.sendAvailabilities(availabilities);

            if(availResponse == null || !availResponse.getStatusInfo().equals(Response.Status.OK)) {
                /* TODO */
                System.out.println("AVAILABILITY ERROR HANDLING");
                System.out.println("ERROR CODE = " + availResponse.getStatus() + ", REASON = " + availResponse.getStatusInfo().getReasonPhrase());
            }
        }
        if(availResponse.getStatusInfo().equals(Response.Status.OK) || compResponse.getStatusInfo().equals(Response.Status.OK)){
            msgToUser = "Your application has been successfully submitted.";
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
        availability = new AvailabilityDTO();
    }
    
    public void deleteEntry(Object entry) {
        if(entry instanceof AvailabilityDTO) {
            availabilities.remove(AvailabilityDTO.class.cast(entry));
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

    public List<AvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    public void setAvailabilities(List<AvailabilityDTO> availabilities) {
        this.availabilities = availabilities;
    }

    public AvailabilityDTO getAvailability() {
        return availability;
    }

    public void setAvailability(AvailabilityDTO availability) {
        this.availability = availability;
    }

    public List<Double> getYearsOfExp() {
        return yearsOfExp;
    }
    
    public List<CompetenceDTO> getCompetences() {
        List<CompetenceDTO> tempList = new ArrayList<>(competences);
        tempList.removeIf(c -> !c.getLanguage().equals(lc.getLanguage()));
        return tempList;
    }

    public void setCompetences(List<CompetenceDTO> competences) {
        this.competences = competences;
    }

    public String getMsgToUser() {
        String s = msgToUser;
        msgToUser = null;
        return s;
    }
    
    

}
