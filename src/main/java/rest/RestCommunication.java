/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datarepresentation.AvailabilityDTO;
import datarepresentation.Competence;
import model.ApplicationDetailsDTO;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import view.Authentication;

/**
 *
 * @author Oscar
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class RestCommunication {

    private final String BASE_URL = "http://localhost:8080/RecruitmentServ/webresources";
    private final String AUTH_PATH = "auth";
    private final String LOGIN_PATH = "login";
    private final String REGISTER_PATH = "register";
    private final String LOGOUT_PATH = "logout";
    private final String APPLY_PATH = "apply";
    private final String APPLICATIONS_PATH = "applications";
    private final String COMPETENCE_PATH = "competence";
    private final String AVAILABILITY_PATH = "availability";
    private final Client client = ClientBuilder.newClient();
    private final String AUTHORIZATION_SCHEMA = "Bearer ";
    private final String LIST_APPLICATIONS_PATH = "listApplications";
    private final String SEARCH_APPLICATION_PATH = "searchApplication";
    private final String GET_APPLICATION_DETAILS_PATH = "getApplicationDetails";
    private final String STATUS_PATH = "changeStatus";
    //private String token = "";
    //private String role = "";

    /**
     * This method sends a login json object to the remote server to login the
     * user.
     *
     * @param json credentials to login with.
     * @return Response the servers response to the login attempt.
     */
    public Response login(JsonObject json) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(AUTH_PATH, LOGIN_PATH));

        Response loginResponse = request.post(Entity.json(json));
        loginResponse.bufferEntity();
        //exctractTokenAndRoleFromResponse(loginResponse);

        return loginResponse;
    }

    /**
     * This method sends a register json object to the remote server to try to
     * register a new user.
     *
     * @param json registration credentials sent to the server.
     * @return Response with the registration attempt.
     */
    public Response register(JsonObject json) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(AUTH_PATH, REGISTER_PATH));

        Response registerResponse = request.post(Entity.json(json));
        registerResponse.bufferEntity();
        //exctractTokenAndRoleFromResponse(registerResponse);

        return registerResponse;
    }

    /**
     * This method sends a logout request to the remote server.
     *
     * @return Response with the remote server status report.
     */
    public Response logout() {
        Invocation.Builder request = getRequestToPath(Arrays.asList(AUTH_PATH, LOGOUT_PATH));
        request = addAuthorizationHeader(request);

        Response logoutResponse = request.get();

        return logoutResponse;
    }

    /**
     * This method requests the competences in a specific language from the
     * remote server.
     *
     * @return Response with the content from the remote server.
     */
    public Response getCompetences() {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLY_PATH));
        request = addAuthorizationHeader(request);
        Response response = request.get();
        return validateResponseStatus(response);
    }

    /**
     * Requests all available job applications from the server
     *
     * @return Response contaning status and all applictions if successful
     */
    public Response listApplications() {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, LIST_APPLICATIONS_PATH));
        request = addAuthorizationHeader(request);
        Response response = request.get();
        return validateResponseStatus(response);
    }

    /**
     * This method requests the competences for recruiter in a specific language
     * from the remote server.
     *
     * @return Response with the content from the remote server.
     */
    public Response getCompetencesForRecruiter() {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, COMPETENCE_PATH));
        request = addAuthorizationHeader(request);
        Response response = request.get();
        return validateResponseStatus(response);
    }

    /**
     * This method sends a list with the users competences to the remote server
     * as json.
     *
     * @param competenceProfiles the competneces this user has.
     * @return Response from the remote server or an error page if the server
     * couldn't/wouldn't handle the request
     */
    public Response sendCompetences(List<Competence> competenceProfiles) {
        GenericEntity<List<Competence>> entity = new GenericEntity<List<Competence>>(competenceProfiles) {
        };

        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLY_PATH, COMPETENCE_PATH));
        request = addAuthorizationHeader(request);

        Response response = request.post(Entity.json(entity));
        validateResponseStatus(response);

        return response;
    }

    /**
     * This method sends a list with the entered availability periods to the
     * remote server.
     *
     * @param availabilities list with the available periods.
     * @return Response from the remote server or an error page if the server
     * couldn't/wouldn't handle the request.
     */
    public Response sendAvailabilities(List<AvailabilityDTO> availabilities) {
        GenericEntity<List<AvailabilityDTO>> entity = new GenericEntity<List<AvailabilityDTO>>(availabilities) {
        };

        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLY_PATH, AVAILABILITY_PATH));
        request = addAuthorizationHeader(request);

        Response response = request.post(Entity.json(entity));
        validateResponseStatus(response);

        return response;
    }

    /**
     * Sends a post request to remote server with user search parameter to get
     * all application matching all search criterias.
     *
     * @param searchParams JsonObject which contains all search parameters
     * @return Response with applications which fulfills every search criteria.
     */
    public Response searchApplication(JsonObject searchParams) {
        GenericEntity<JsonObject> entity = new GenericEntity<JsonObject>(searchParams) {
        };

        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, SEARCH_APPLICATION_PATH));
        request = addAuthorizationHeader(request);

        Response response = request.post(Entity.json(entity));
        validateResponseStatus(response);

        return response;
    }

    public Response getApplicationDetails(long applicationId) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, GET_APPLICATION_DETAILS_PATH));
        request.header("applicationId", applicationId);
        request = addAuthorizationHeader(request);
        Response response = request.get();
        return validateResponseStatus(response);
    }

    /*private void exctractTokenAndRoleFromResponse(Response response) {
        JsonObject json = response.readEntity(JsonObject.class);
        token = json.getString("token", "");
        role = json.getString("role", "");
        System.out.println("CLINET RECEIVED TOKEN: " + token + ", ROLE: " + role);
    }*/
    /**
     * Adds token to target header for user validation in server side.
     *
     * @param target Invocation target
     * @return target with token
     */
    private Invocation.Builder addAuthorizationHeader(Invocation.Builder target) {
        String token;
        try {
            token = Authentication.token;
        } catch (Exception ex) {
            token = "";
        }

        return target.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_SCHEMA + token);
    }

    /**
     * Build a new webtarget with provides base url and inner paths.
     *
     * @param paths inner paths
     * @return reequested webtarget
     */
    private Invocation.Builder getRequestToPath(List<String> paths) {
        WebTarget target = client.target(BASE_URL);

        for (String path : paths) {
            target = target.path(path);
        }

        return target.request();
    }

    /**
     * Validates if the response from server is acceptable. If not send user to
     * error page with error msg.
     *
     * @param response Response contaning response status
     * @return Response object
     */
    private Response validateResponseStatus(Response response) {
        switch (response.getStatus()) {
            case 401:
                System.out.println("401 bsnitch");
                triggerError(401, "BLOCKING YOU BeaCH");
                break;
            case 403:
                System.out.println("403 forbidden");
                triggerError(403, "I FORBID YOU!");
                break;
            case 400:
                triggerError(400, "Bad Request");
                break;
            default:
                System.out.println("defaulting: " + response.getStatus() + ", status: " + response.getStatusInfo());
                break;
        }

        return response;
    }

    /**
     * Send user to error page with provided error message.
     *
     * @param code error code
     * @param msg error message
     */
    private void triggerError(int code, String msg) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(code, msg);
            FacesContext.getCurrentInstance().responseComplete();

        } catch (IOException ex) {
            System.out.println("ERROR CAUGHT : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void changeAppStatus(JsonObject obj) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, STATUS_PATH));
        request = addAuthorizationHeader(request);
        Response response = request.post(Entity.json(obj));
        validateResponseStatus(response);
    }
}
