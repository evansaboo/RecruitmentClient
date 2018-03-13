/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import model.ApplicationDetailsDTO;
import model.CompetenceProfileDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import logger.LoggHandler;
import model.LanguageChange;
import model.StatusNameDTO;
import rest.RestCommunication;

/**
 * Application Overview i used to render the Application Overview xhtml page
 * with JSF
 *
 * @author Evan
 */
@Named("applicationOverview")
@SessionScoped
public class ApplicationOverview implements Serializable {

    private static final long serialVersionUID = 1L;
    private long applicationId;
    private ApplicationDetailsDTO appDetails = new ApplicationDetailsDTO();
    private String msgToUser;
    private String password;
    private final Map<String, List<CompetenceProfileDTO>> competenceHashMap = new HashMap<>();
    @Inject
    private RestCommunication rc;

    @Inject
    private LanguageChange lc;

    /**
     * Return recruiter password
     *
     * @return password of reqruiter
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set reqruiter password
     *
     * @param password reqruiter password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    LoggHandler log = new LoggHandler();

    /**
     * Initilizes the page by getting job application details from server and
     * rendering it to xhtml page with JSF
     */
    public void initPage() {
        Response response = rc.getApplicationDetails(applicationId);

        if (response.getStatus() != 200) {
            log.logErrorMsg("Could not get application (application ID = " + applicationId + ") details  from server, ERROR CODE: " + response.getStatus(), Level.INFO, null);

            return;
        }
        appDetails = response.readEntity(new GenericType<ApplicationDetailsDTO>() {
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
    }

    /**
     * Gets the value of applicationId property
     *
     * @return applicationId as long
     */
    public long getApplicationId() {
        return applicationId;
    }

    /**
     * Sets the applicationId property
     *
     * @param applicationId the applicationId to set
     */
    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    /**
     * Gets the value of appDetails property
     *
     * @return applicationId as ApplicationDetailsDTO object
     */
    public ApplicationDetailsDTO getAppDetails() {
        return appDetails;
    }

    /**
     * Sets the appDetails property
     *
     * @param app the appDetails to set
     */
    public void setAppDetails(ApplicationDetailsDTO app) {
        this.appDetails = app;
    }

    /**
     * Gets the value of statusName property
     *
     * @return statusName as String object
     */
    public String getStatus() {
        String statusName = null;
        for (StatusNameDTO status : appDetails.getStatusName()) {
            if (status.getSupportedLanguage().equals(lc.getLanguage())) {
                statusName = status.getName();
                break;
            }
        }
        return statusName;
    }

    /**
     * Gets a list if all Competence profiles
     *
     * @return CompetenceProfiles as a List of CompetenceProfileDTO1 objects
     */
    public List<CompetenceProfileDTO> getCompetenceProfiles() {
        return competenceHashMap.get(lc.getLanguage());
    }

    /**
     * Returns message to display for user
     *
     * @return message for user
     */
    public String getMsgToUser() {
        String temp = msgToUser;
        msgToUser = null;
        return temp;
    }

    /**
     * Sees that the user entered the correct password
     *
     * @param status status to change
     * @throws Exception
     */
    public void authenticateSubmit(String status) throws Exception {

        JsonObject job = JsonProvider.provider().createObjectBuilder()
                .add("password", password).build();
        Response validateResponse = rc.validate(job);
        if (validateResponse.getStatus() == 204) {
            changeStatus(status);

        } else if (validateResponse.getStatus() == 400) {
            parseMsgToUser(lc.getLangProperty("errorMsg_creds"), "danger");
        }

    }

    /**
     * Change current application status by sending the status to server
     *
     * @param status used to change Status i the application
     */
    public void changeStatus(String status) {
        JsonObject jbuilder = Json.createObjectBuilder()
                .add("applicationId", applicationId)
                .add("appStatus", status)
                .build();

        Response response = rc.changeAppStatus(jbuilder);

        if (response.getStatus() != Response.Status.OK.getStatusCode()) {
            log.logErrorMsg("Something went wrong when changing status, ERROR CODE:" + response.getStatus(), Level.WARNING, null);
            return;
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (IOException ex) {
            log.logErrorMsg("Something went wrong when reloading application overview page.", Level.WARNING, ex);
        }
    }

    /**
     * Generates a pdf of the application on successful retreival, else
     * generates an error message to the user depending on the error.
     */
    public void generatePdf() {
        Response response = rc.generatePdf(applicationId);

        try {
            if (response.getStatus() == Response.Status.OK.getStatusCode()) {
                setFacesContextToPdf(response);
            } else if (response.getStatus() == Response.Status.EXPECTATION_FAILED.getStatusCode()) {
                log.logErrorMsg("Could not generate pdf, ERROR CODE: " + response.getStatus(), Level.WARNING, null);

                parseMsgToUser("The remote server had a problem generating the desired pdf", "danger");
            }
        } catch (Exception e) {
            log.logErrorMsg("Could not generate PDF because, ERROR: " + e.getMessage(), Level.SEVERE, e);
        }

    }

    /**
     * Sets the faces context to pdf
     *
     * @param response from server
     * @throws Exception if Response doesn't have any entity to read
     */
    private void setFacesContextToPdf(Response response) throws Exception {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletResponse httpResponse = (HttpServletResponse) context.getExternalContext().getResponse();
        httpResponse.reset();
        httpResponse.setContentType("application/pdf");
        //httpResponse.setHeader("Content-Disposition", "inline; filename=file.pdf");

        byte[] pdf = response.readEntity(new GenericType<byte[]>() {
        });
        httpResponse.getOutputStream().write(pdf);
        httpResponse.getOutputStream().flush();
        httpResponse.getOutputStream().close();
        context.responseComplete();
    }

    /**
     * Parses message to user by combining msg with msgType
     *
     * @param msg message to user
     * @param msgType message type
     */
    private void parseMsgToUser(String msg, String msgType) {
        msgToUser = msg + "##" + msgType;
    }
}
