/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import datarepresentation.CompetenceDTO;
import datarepresentation.CompetenceProfileDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
    private List<CompetenceDTO> competences;
    private List<CompetenceProfileDTO> profiles = new ArrayList<>();
    private CompetenceProfileDTO profile = new CompetenceProfileDTO();
    private HashMap<String, Long> competenceMapper = new HashMap<>();
    private final Client client = ClientBuilder.newClient();
    private final List<Integer> years = IntStream.range(0, 125).boxed().collect(Collectors.toList());
    
    @PostConstruct
    private void populateCompetences() {
        competences = client.target(BASE_URL)
                .request().get(new GenericType<List<CompetenceDTO>> () {});
        
        competences.forEach((CompetenceDTO comp) -> {
            competenceMapper.put(comp.getName(), comp.getCompetenceId());
        });
    }

    public void submitApplication() {
        if(profiles.isEmpty()) { return; }
        
        GenericEntity<List<CompetenceProfileDTO>> entity = new GenericEntity<List<CompetenceProfileDTO>>(profiles) {};
        Response response = client.target(BASE_URL).request().post(Entity.json(entity));
        profiles = new ArrayList<>();
    }
    
    public void addProfile() {
        if(profile.getName() == null) { return; }
        Long compId = competenceMapper.get(profile.getName());
        profile.setCompetenceId(compId);
        profiles.add(profile);
        profile = new CompetenceProfileDTO();
    }
    
    public void deleteProfile(CompetenceProfileDTO prof) {
        profiles.remove(prof);
    }

    public List<CompetenceDTO> getCompetences() {
        return competences;
    }

    public void setCompetences(List<CompetenceDTO> competences) {
        this.competences = competences;
    }

    public List<CompetenceProfileDTO> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<CompetenceProfileDTO> profiles) {
        this.profiles = profiles;
    }

    public CompetenceProfileDTO getProfile() {
        return profile;
    }

    public void setProfile(CompetenceProfileDTO profile) {
        this.profile = profile;
    }

    public HashMap<String, Long> getCompetenceMapper() {
        return competenceMapper;
    }

    public void setCompetenceMapper(HashMap<String, Long> competenceMapper) {
        this.competenceMapper = competenceMapper;
    }

    public List<Integer> getYears() {
        return years;
    }
    
}