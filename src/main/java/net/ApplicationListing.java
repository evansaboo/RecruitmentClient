/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import rest.RestCommunication;
import datarepresentation.CompetenceDTO;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.GenericType;
import model.Application;
import model.LanguageChange;

@Named("applicationListing")
@SessionScoped
public class ApplicationListing implements Serializable {

    private Date regDate;
    private Date periodFrom;
    private Date periodTo;
    private String competence;
    private String firstname;
    String PATH = "applications";

    private List<CompetenceDTO> cmptList;
    private final ArrayList<Application> applications = new ArrayList<>();

    @Inject
    private RestCommunication contr;

    @Inject
    private LanguageChange lc;

    public void initPage() {
        cmptList = contr.getCompetencesForRecruiter(lc.getLanguage()).readEntity(new GenericType<List<CompetenceDTO>>() {
        });

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

    public List<CompetenceDTO> getCmptList() {
        return cmptList;
    }

    public void setCmptList(List<CompetenceDTO> cmptList) {
        this.cmptList = cmptList;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    private void initList() {
        applications.clear();
        JsonArray jarray = contr.listApplications().readEntity(new GenericType<JsonArray>() {});
        for (int i = 0; i < jarray.size(); i++) {
            JsonObject obj = jarray.getJsonObject(i);
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
        JsonArray s = contr.searchApplication(jbuilder).readEntity(new GenericType<JsonArray>(){});
        applications.clear();
        for (int i = 0; i < s.size(); i++) {
            JsonObject obj = s.getJsonObject(i);

            applications.add(new Application(Long.parseLong(obj.getString("applicationId")),
                    obj.getString("firstname"),
                    obj.getString("surname"),
                    obj.getString("email")));
        }
    }

    public void updateCompetences() {
        cmptList = contr.getCompetencesForRecruiter(lc.getLanguage()).readEntity(new GenericType<List<CompetenceDTO>>() {
        });
    }
}
