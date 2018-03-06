/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.ApplicationDetailsDTO;
import datarepresentation.CompetenceProfileDTO1;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import model.LanguageChange;
import rest.RestCommunication;

/**
 *
 * @author Evan
 */
@Named("applicationOverview")
@SessionScoped
public class ApplicationOverview implements Serializable {
    private static final long serialVersionUID = 1L;
    private long applicationId;
    private ApplicationDetailsDTO appDetails = new ApplicationDetailsDTO();

    private final Map<String, List<CompetenceProfileDTO1>> competenceHashMap = new HashMap<>();
    @Inject
    private RestCommunication rc;

    @Inject
    private LanguageChange lc;

    public void initPage() {
        try {
            appDetails = rc.getApplicationDetails(applicationId).readEntity(new GenericType<ApplicationDetailsDTO>() {
            });
            competenceHashMap.clear();
            appDetails.getCompetenceProfiles().stream().map((cp) -> {
                if (!competenceHashMap.containsKey(cp.getLanguage())) {
                    competenceHashMap.put(cp.getLanguage(), new ArrayList<>());
                }
                return cp;
            }).forEachOrdered((cp) -> {
                competenceHashMap.get(cp.getLanguage()).add(cp);
            });
        } catch (Exception ex) {
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
    
    /**
     * Generates a pdf of the application on successful retreival, else generates 
     * an error message to the user depending on the error. 
     */
    public void generatePdf() {
        System.out.println("Generate pdf for application: " + applicationId);
        Response response = rc.generatePdf(applicationId);
        
        System.out.println("Response status: " + response.getStatusInfo());
        
        try {
            if(response.getStatus() == Response.Status.OK.getStatusCode()) {
                setFacesContextToPdf(response);
            } else if(response.getStatus() == Response.Status.EXPECTATION_FAILED.getStatusCode()) {
                // show error msg with "The remote server had a problem generating the desired pdf." 
                System.out.println("Error message");
            } else {
                // show error msg with "Unknown problem encountered while retreiving pdf." 
                System.out.println("Generic Error message");
            }
        } catch(Exception ex) {
            // show error msg with "Unable to display the pdf." 
        }
        
    }
    
    private void setFacesContextToPdf(Response response) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse httpResponse = (HttpServletResponse) context.getExternalContext().getResponse();
        httpResponse.reset();
        httpResponse.setContentType("application/pdf");
        //httpResponse.setHeader("Content-Disposition", "inline; filename=file.pdf");

        byte[] pdf = response.readEntity(new GenericType<byte[]>() {});
        httpResponse.getOutputStream().write(pdf);
        httpResponse.getOutputStream().flush();
        httpResponse.getOutputStream().close();
        context.responseComplete();
    }
}
