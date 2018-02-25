/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.ApplicationDetailsDTO;
import datarepresentation.CompetenceDTO;
import datarepresentation.CompetenceProfileDTO1;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.GenericType;
import model.LanguageChange;
import rest.RestCommunication;

/**
 *
 * @author Evan
 */
@Named("applicationOverview")
@SessionScoped
public class ApplicationOverview implements Serializable {

    private long applicationId;
    private ApplicationDetailsDTO appDetails = new ApplicationDetailsDTO();

    private final Map<String, List<CompetenceProfileDTO1>> competenceHashMap = new HashMap<>();
    @Inject
    private RestCommunication rc;

    @Inject
    private LanguageChange lc;

    public void initPage() {
        appDetails = rc.getApplicationDetails(applicationId).readEntity(new GenericType<ApplicationDetailsDTO>() {
        });
        competenceHashMap.clear();
        for (CompetenceProfileDTO1 cp : appDetails.getCompetenceProfiles()) {
            if (!competenceHashMap.containsKey(cp.getLanguage())) {
                competenceHashMap.put(cp.getLanguage(), new ArrayList<>());
            }
            competenceHashMap.get(cp.getLanguage()).add(cp);
        }
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public ApplicationDetailsDTO getAppDetails() {
        return appDetails;
    }

    public void setAppDetails(ApplicationDetailsDTO app) {
        this.appDetails = app;
    }

    public String getStatus() {
        return appDetails.getStatusName().get(lc.getLanguage());
    }

    public void setStatus(String status) {
        appDetails.getStatusName().put(lc.getLanguage(), status);
    }

    public List<CompetenceProfileDTO1> getCompetenceProfiles() {
        return competenceHashMap.get(lc.getLanguage());
    }

    public void changeStatus(long status) {
        JsonObject jbuilder = Json.createObjectBuilder()
                .add("applicationId", applicationId)
                .add("appStatus", status)
                .build();
        rc.changeAppStatus(jbuilder);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            Logger.getLogger(ApplicationOverview.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
