/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import model.Application;

@Named("applicationListing")
@SessionScoped
public class ApplicationListing implements Serializable {

    private Date date;
    private String timeFrom;
    private String timeTo;
    private String competence;
    private String firstname;

    private final Map<String, Integer> cmptList = new LinkedHashMap<>();
    private final ArrayList<Application> applications = new ArrayList<>();

    @PostConstruct
    public void init() {
        JsonObject jbuilder = Json.createObjectBuilder().add("type", "getAllCompetences").build();
        JsonArray s = getArrayFromServer(jbuilder, "/initAppListing");
        for (int i = 0; i < s.size(); i++) {
            JsonObject obj = s.getJsonObject(i);
            cmptList.put(obj.getString("competenceName"), obj.getInt("competenceId"));
        }
        
        initList();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public Map<String, Integer> getCmptList() {
        return cmptList;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    private void initList() {
        JsonObject jbuilder = Json.createObjectBuilder().add("type", "getAllJobApplications").build();
        JsonArray s = getArrayFromServer(jbuilder, "/initAppListing"); 
            for (int i = 0; i < s.size(); i++) {
            JsonObject obj = s.getJsonObject(i);
            applications.add(new Application(obj.getString("firstname"), obj.getString("surname"), obj.getString("email")));
        }
    }
    
    
    public JsonArray getArrayFromServer(JsonObject obj, String path) {
        Client client = ClientBuilder.newClient();
        JsonArray s = client.target("http://localhost:8080/RecruitmentServ/webresources/applications"+path)
                .request()
                .post(Entity.entity(obj, MediaType.APPLICATION_JSON), JsonArray.class);
        return s;
    }
    
    public void searchApplications(){
        DateFormat dFormat = new SimpleDateFormat("d-MM-yyyy");
        String sDate = (date != null) ? dFormat.format(date): "";
        JsonObject jbuilder = Json.createObjectBuilder()
                .add("type", "searchApplications")
                .add("subDate", sDate)
                .add("periodFrom", timeFrom)
                .add("periodTo", timeTo)
                .add("competence", competence)
                .add("name", firstname)
                .build();
        JsonArray s = getArrayFromServer(jbuilder, "/searchApplications"); 
            applications.clear();
            for (int i = 0; i < s.size(); i++) {
            JsonObject obj = s.getJsonObject(i);
            
            applications.add(new Application(obj.getString("firstname"), obj.getString("surname"), obj.getString("email")));
        } 
    }

}
