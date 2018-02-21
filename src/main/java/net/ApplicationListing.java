/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import model.Application;

@Named("applicationListing")
@SessionScoped
public class ApplicationListing implements Serializable {

    private Date regDate;
    private Date periodFrom;
    private Date periodTo;
    private String competence;
    private String firstname;
    String PATH = "applications";

    private final Map<String, Integer> cmptList = new LinkedHashMap<>();
    private final ArrayList<Application> applications = new ArrayList<>();

    @Inject
    private ServerCommunication sc;

    @PostConstruct
    public void init() {
        JsonObject jbuilder = Json.createObjectBuilder().add("type", "getAllCompetences").build();
        JsonArray s = sc.postJson(PATH + "/initAppListing", jbuilder, JsonArray.class);
        for (int i = 0; i < s.size(); i++) {
            JsonObject obj = s.getJsonObject(i);
            cmptList.put(obj.getString("competenceName"), obj.getInt("competenceId"));
        }

        initList();
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date date) {
        this.regDate = date;
    }

    public Date getPeriodFrom() {
        return periodFrom;
    }

    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = periodFrom;
    }

    public Date getPeriodTo() {
        return periodTo;
    }

    public void setPeriodTo(Date periodTo) {
        this.periodTo = periodTo;
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
        JsonArray s = sc.postJson(PATH + "/initAppListing", jbuilder, JsonArray.class);
        for (int i = 0; i < s.size(); i++) {
            JsonObject obj = s.getJsonObject(i);
            applications.add(new Application(Long.parseLong(obj.getString("applicationId")),
                    obj.getString("firstname"),
                    obj.getString("surname"),
                    obj.getString("email")));
        }
    }

    public void searchApplications() {
        DateFormat dFormat = new SimpleDateFormat("d-MM-yyyy");
        String sDate = (regDate != null) ? dFormat.format(regDate) : "";
        String pFrom = (periodFrom != null) ? dFormat.format(periodFrom) : "";
        String pTo = (periodTo != null) ? dFormat.format(periodTo) : "";
        JsonObject jbuilder = Json.createObjectBuilder()
                .add("subDate", sDate)
                .add("periodFrom", pFrom)
                .add("periodTo", pTo)
                .add("competence", competence)
                .add("name", firstname)
                .build();
        JsonArray s = sc.postJson(PATH + "/searchApplications", jbuilder, JsonArray.class);
        applications.clear();
        for (int i = 0; i < s.size(); i++) {
            JsonObject obj = s.getJsonObject(i);

            applications.add(new Application(Long.parseLong(obj.getString("applicationId")),
                    obj.getString("firstname"),
                    obj.getString("surname"),
                    obj.getString("email")));
        }
    }

}
