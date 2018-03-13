/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import rest.RestCommunication;
import model.AvailabilityDTO;
import model.CompetenceDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import model.CompetenceProfileDTO;
import logger.LoggHandler;
import model.LanguageChange;

/**
 * Handles the view where applicants can create new applications.
 *
 * @author Oscar
 */
@Named("applyManager")
@SessionScoped
public class ApplyManager implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private RestCommunication restCom;
    @Inject
    private LanguageChange lc;

    private List<CompetenceDTO> competences;
    private final HashMap<String, Long> competenceMapper = new HashMap<>();

    private List<CompetenceProfileDTO> compProfiles = new ArrayList<>();
    private CompetenceProfileDTO compProfile = new CompetenceProfileDTO();
    private List<AvailabilityDTO> availabilities = new ArrayList<>();
    private AvailabilityDTO availability = new AvailabilityDTO();
    private final List<Double> yearsOfExp = new ArrayList<>();
    private String msgToUser;


    private final LoggHandler log = new LoggHandler();

    /**
     * Initializes page by fetching relevant data
     */
    public void onPageLoad() {
        if (competences == null || competences.isEmpty()) {
            Response competencesResponse = restCom.getCompetences();

            if (competencesResponse.getStatus() != Response.Status.OK.getStatusCode()) {
                log.logErrorMsg("Could not get Competences from server, ERROR CODE: " + competencesResponse.getStatus(), Level.WARNING, null);
                return;
            }

            competences = competencesResponse.readEntity(new GenericType<List<CompetenceDTO>>() {
            });

            competences.forEach(compet -> {
                competenceMapper.put(compet.getName(), compet.getCompetenceId());
            });

            double interval = 0.25;
            for (double i = interval; i <= 75; i += interval) {
                yearsOfExp.add(i);
            }
        }
    }
    /**
     * Sees that the user entered the correct password
     * @param pass user password
     * @throws Exception 
     */
    public void authenticateSubmit(String pass) throws Exception {

        JsonObject job = JsonProvider.provider().createObjectBuilder()
                    .add("password", pass).build();
        Response validateResponse = restCom.validate(job);
        
        if (validateResponse.getStatus() == 204){
            submitApplication();
        }else{
            parseMsgToUser(lc.getLangProperty("errorMsg_creds"), "danger");
        }
        
    }
    /**
     * Submits an application to server
     *
     */
    public void submitApplication() {
        if (compProfiles.isEmpty() && availabilities.isEmpty()) {
            return;
        }
        //authenticate
        Response availResponse = Response.noContent().build();
        Response compResponse = Response.noContent().build();

        if (!compProfiles.isEmpty()) {
            compResponse = restCom.sendCompetences(compProfiles);
        }

        if (!availabilities.isEmpty()) {
            availResponse = restCom.sendAvailabilities(availabilities);
        }

        if (availResponse.getStatus() == Response.Status.NO_CONTENT.getStatusCode()
                && compResponse.getStatus() == Response.Status.NO_CONTENT.getStatusCode()) {
            parseMsgToUser(lc.getLangProperty("success_apply"), "success");
        } else {

            log.logErrorMsg("Could not get submit application, ERROR CODE: " + availResponse.getStatus(), Level.WARNING, null);
            parseMsgToUser(lc.getLangProperty("errorMsg_creds"), "danger");
        }

        compProfiles = new ArrayList<>();
        availabilities = new ArrayList<>();
    }

    /**
     * Adds a competence to applicant
     */
    public void addCompetence() {
        if (compProfile.getCompetenceName() == null) {
            return;
        }

        Long compId = competenceMapper.get(compProfile.getCompetenceName());
        compProfile.setCompetenceId(compId);
        compProfiles.add(compProfile);
        compProfile = new CompetenceProfileDTO();
    }

    /**
     * Adds an availability to the applicant
     */
    public void addAvailability() {
        if (availability.getToDate() == null || availability.getFromDate() == null) {
            return;
        }
        availabilities.add(availability);
        availability = new AvailabilityDTO();
    }

    /**
     * Delete an availability or competenceprofile
     *
     * @param entry entry to be deleted
     */
    public void deleteEntry(Object entry) {
        if (entry instanceof AvailabilityDTO) {
            availabilities.remove(AvailabilityDTO.class.cast(entry));
        } else if (entry instanceof CompetenceProfileDTO) {
            compProfiles.remove(CompetenceProfileDTO.class.cast(entry));
        }
    }

    /**
     * Returna a list of competences
     *
     * @return list of competences
     */
    public List<CompetenceDTO> getCompetences() {
        List<CompetenceDTO> tempComp = competences == null ? new ArrayList<>() : new ArrayList<>(competences);
        tempComp.removeIf(comp -> !comp.getLanguage().equals(lc.getLanguage()));
        return tempComp;
    }

    /**
     * Returna a list of availabilities
     *
     * @return list of avaliabilities
     */
    public List<CompetenceProfileDTO> getComps() {
        return compProfiles;
    }

    /**
     * Sets the competences
     *
     * @param comps list of competences
     */
    public void setComps(List<CompetenceProfileDTO> comps) {
        this.compProfiles = comps;
    }

    /**
     * Returns a competence profile
     *
     * @return competence profile
     */
    public CompetenceProfileDTO getComp() {
        return compProfile;
    }

    /**
     * Sets a competece profile
     *
     * @param comp competence profile
     */
    public void setComp(CompetenceProfileDTO comp) {
        this.compProfile = comp;
    }

    /**
     * Return availabilities
     *
     * @return list of availabilities
     */
    public List<AvailabilityDTO> getAvailabilities() {
        return availabilities;
    }

    /**
     * Return single availabilitiy
     *
     * @return an availability
     */
    public AvailabilityDTO getAvailability() {
        return availability;
    }

    /**
     * Set an avalability
     *
     * @param availability an applicants availability
     */
    public void setAvailability(AvailabilityDTO availability) {
        this.availability = availability;
    }

    /**
     * Returns a list of years of experience
     *
     * @return list of years of experience
     */
    public List<Double> getYearsOfExp() {
        return yearsOfExp;
    }

    /**
     * Returns a message that will be displayed to the user
     *
     * @return message for the user
     */
    public String getMsgToUser() {
        String s = msgToUser;
        msgToUser = null;
        return s;
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
