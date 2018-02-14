/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

@Named("applicationListing")
@SessionScoped
public class ApplicationListing implements Serializable {

    private String date;
    private String timeFrom;
    private String timeTo;
    private String competence;
    private String firstname;
    private final Map<String, Integer> cmptList = new LinkedHashMap<>();

    @PostConstruct
    public void init() {
        JsonObject jbuilder = Json.createObjectBuilder().add("type", "getCompetence").build();
        Client client = ClientBuilder.newClient();
        JsonArray s = client.target("http://localhost:8080/RecruitmentServ/webresources/applications")
                .request()
                .post(Entity.entity(jbuilder, MediaType.APPLICATION_JSON), JsonArray.class);
        
        for(int i = 0; i < s.size(); i++){
            JsonObject obj = s.getJsonObject(i);
            cmptList.put(obj.getString("competenceName"),obj.getInt("competenceId"));
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

    public String getCompetence() {
        return competence;
    }

    public void setCompetence(String competence) {
        this.competence = competence;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String name) {
        this.firstname = name;
    }
    public Map getCompetenceList() {
        return cmptList;
    }
}
