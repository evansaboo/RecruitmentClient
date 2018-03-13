/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import rest.RestCommunication;
import model.CompetenceDTO;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import model.Application;
import logger.LoggHandler;
import model.LanguageChange;

/**
 * Handles all applications
 *
 * @author Emil
 */
@Named("applicationListing")
@SessionScoped
public class ApplicationListing implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date regDate;
    private Date periodFrom;
    private Date periodTo;
    private String competence;
    private String firstname;

    private List<CompetenceDTO> cmptList = new ArrayList<>();
    private final ArrayList<Application> applications = new ArrayList<>();

    @Inject
    private RestCommunication restCom;

    @Inject
    private LanguageChange lc;

    LoggHandler log = new LoggHandler();

    /**
     * Fills the list of competences on initialize
     */
    public void initPage() {
        Response response = restCom.getCompetencesForRecruiter();
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            log.logErrorMsg("Could not initialize Application listing page, ERROR CODE: "+ response.getStatus(), Level.INFO, null);
            return;
        }
        cmptList = response.readEntity(new GenericType<List<CompetenceDTO>>() {
        });
        initList();
    }

    /**
     * Returns the submission date
     *
     * @return date of submission
     */
    public Date getRegDate() {
        return lc.parseDateAfterLocale(regDate);

    }

    /**
     * Set the date of submission
     *
     * @param date The date entered by the user for submission
     */
    public void setRegDate(Date date) {
        this.regDate = lc.parseDateAfterLocale(date);
    }

    /**
     * Return the period from when the applicant is available
     *
     * @return the date from when the applicant is available
     */
    public Date getPeriodFrom() {
        return lc.parseDateAfterLocale(periodFrom);
    }

    /**
     * Set the period from which the applicant is available
     *
     * @param periodFrom The date entered by the user
     */
    public void setPeriodFrom(Date periodFrom) {
        this.periodFrom = lc.parseDateAfterLocale(periodFrom);
    }

    /**
     * Set the period to where the applicant is available
     *
     * @return returns the period of
     */
    public Date getPeriodTo() {
        return lc.parseDateAfterLocale(periodTo);
    }

    /**
     * Set the period to which the applicant is available
     *
     * @param periodTo The period as entered by the applicant
     */
    public void setPeriodTo(Date periodTo) {
        this.periodTo = lc.parseDateAfterLocale(periodTo);
    }

    /**
     * Return a competence
     *
     * @return a competence
     */
    public String getCompetence() {
        return competence;
    }

    /**
     * Set a competence
     *
     * @param competence competence picked by the user
     */
    public void setCompetence(String competence) {
        this.competence = competence;
    }

    /**
     * Returns the applicants first name
     *
     * @return first name entered
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Set the first name entered by the applicant
     *
     * @param name the first name from the applicant
     */
    public void setFirstname(String name) {
        this.firstname = name;
    }

    /**
     * Returns a list of competences
     *
     * @return list of competences
     */
    public List<CompetenceDTO> getCmptList() {
        List<CompetenceDTO> tempList = new ArrayList<>(cmptList);
        tempList.removeIf(c -> !c.getLanguage().equals(lc.getLanguage()));
        return tempList;
    }

    /**
     * Set list of competences
     *
     * @param cmptList a list of competences
     */
    public void setCmptList(List<CompetenceDTO> cmptList) {
        this.cmptList = cmptList;
    }

    /**
     * Returns a list of applications
     *
     * @return list of applications
     */
    public ArrayList<Application> getApplications() {
        return applications;
    }

    /**
     * Initializes the list of applications and fills it with all applications.
     */
    private void initList() {
        applications.clear();
        JsonArray jarray = restCom.listApplications().readEntity(new GenericType<JsonArray>() {
        });
        for (int i = 0; i < jarray.size(); i++) {
            JsonObject obj = jarray.getJsonObject(i);
            applications.add(new Application(Long.parseLong(obj.getString("applicationId")),
                    obj.getString("firstname"),
                    obj.getString("surname"),
                    obj.getString("email")));
        }
    }

    /**
     * Search for specific applications and display them based on the results
     */
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

        Response response = restCom.searchApplication(jbuilder);
        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            log.logErrorMsg("Could not get applications by using search form, ERROR CODE: "+ response.getStatus(), Level.INFO, null);

            return;
        }
        JsonArray s = response.readEntity(new GenericType<JsonArray>() {
        });
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
