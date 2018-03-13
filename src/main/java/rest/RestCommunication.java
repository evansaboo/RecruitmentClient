/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import model.AvailabilityDTO;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import model.CompetenceProfileDTO;
import logger.LoggHandler;
import model.LanguageChange;
import view.Authentication;
import view.ErrorView;

/**
 * Handles the communication with the remote REST server, all remote calls go
 * through here and this class also handles the authentication and authorizaton
 * errors.
 *
 * @author Oscar
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless

public class RestCommunication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private LanguageChange languageChange;
    @Inject
    private Authentication auth;

    @Inject
    private ErrorView error;

    private final Client client = ClientBuilder.newClient();
    private final static String BASE_URL = "http://localhost:8080/RecruitmentServ/webresources";
    private final static String AUTH_PATH = "auth";
    private final static String LOGIN_PATH = "login";
    private final static String VALIDATE_PATH = "validate";
    private final static String REGISTER_PATH = "register";
    private final static String LOGOUT_PATH = "logout";
    private final static String APPLY_PATH = "apply";
    private final static String APPLICATIONS_PATH = "applications";
    private final static String COMPETENCE_PATH = "competence";
    private final static String AVAILABILITY_PATH = "availability";
    private final static String AUTHORIZATION_SCHEMA = "Bearer ";
    private final static String LIST_APPLICATIONS_PATH = "listApplications";
    private final static String SEARCH_APPLICATION_PATH = "searchApplication";
    private final static String GET_APPLICATION_DETAILS_PATH = "getApplicationDetails";
    private final static String PDF_PATH = "pdf";
    private final static String STATUS_PATH = "changeStatus";

    /**
     * This method sends a login json object to the remote server to login the
     * user.
     *
     * @param json credentials to login with.
     * @return Response the servers response to the login attempt.
     */
    public Response login(JsonObject json) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(AUTH_PATH, LOGIN_PATH));

        return sendPostRequest(request, Entity.json(json));
    }

    /**
     * This method sends a validate json object to the remote server to validate
     * the user.
     *
     * @param json credentials to validate with.
     * @return Response the servers response to the validation attempt.
     */
    public Response validate(JsonObject json) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(AUTH_PATH, VALIDATE_PATH));
        request = addAuthorizationHeader(request);
        return sendPostRequest(request, Entity.json(json));
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

        return sendPostRequest(request, Entity.json(json));
    }

    /**
     * This method sends a logout request to the remote server.
     *
     * @return Response with the remote server status report.
     */
    public Response logout() {
        Invocation.Builder request = getRequestToPath(Arrays.asList(AUTH_PATH, LOGOUT_PATH));
        request = addAuthorizationHeader(request);

        //FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return sendGetRequest(request);
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

        return sendGetRequest(request);
    }

    /**
     * Requests all available job applications from the server
     *
     * @return Response contaning status and all applictions if successful
     */
    public Response listApplications() {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, LIST_APPLICATIONS_PATH));
        request = addAuthorizationHeader(request);

        return sendGetRequest(request);
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

        return sendGetRequest(request);
    }

    /**
     * This method sends a list with the users competences to the remote server
     * as json.
     *
     * @param competenceProfiles the competneces this user has.
     * @return Response from the remote server or an error page if the server
     * couldn't/wouldn't handle the request
     */
    public Response sendCompetences(List<CompetenceProfileDTO> competenceProfiles) {
        GenericEntity<List<CompetenceProfileDTO>> entity
                = new GenericEntity<List<CompetenceProfileDTO>>(competenceProfiles) {
        };

        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLY_PATH, COMPETENCE_PATH));
        request = addAuthorizationHeader(request);

        return sendPostRequest(request, Entity.json(entity));
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

        return sendPostRequest(request, Entity.json(entity));
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

        return sendPostRequest(request, Entity.json(entity));
    }

    /**
     * Calls the remote server and asks for a pdf with the applications details
     * belonging to a specific application id.
     *
     * @param applicationId the specific application id.
     * @return Response with the resopnse from the server.
     */
    public Response generatePdf(long applicationId) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(
                APPLICATIONS_PATH,
                GET_APPLICATION_DETAILS_PATH,
                PDF_PATH,
                "" + applicationId)
        );
        request = addAuthorizationHeader(request);
        addLocaleHeader(request);

        return sendGetRequest(request);
    }

    /**
     * Returns application details for certain application
     *
     * @param applicationId Id for an application
     * @return details for an application
     */
    public Response getApplicationDetails(long applicationId) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, GET_APPLICATION_DETAILS_PATH));
        request.header("applicationId", applicationId);
        request = addAuthorizationHeader(request);

        return sendGetRequest(request);
    }

    /**
     * Adds token to target header for user validation in server side.
     *
     * @param target Invocation target
     * @return target with token
     */
    private Invocation.Builder addAuthorizationHeader(Invocation.Builder target) {
        String token;
        try {
            token = auth.getToken();
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
        response.bufferEntity();

        switch (response.getStatus()) {
            case 401:
                triggerError(401, languageChange.getLangProperty("error401"));
                break;
            case 403:
                triggerError(403, languageChange.getLangProperty("error403"));
                break;
            case 404:
                triggerError(404, languageChange.getLangProperty("error404"));
                break;
            case 406:
                triggerError(406, languageChange.getLangProperty("error406"));
                break;
            case 500:
                triggerError(500, languageChange.getLangProperty("error500"));
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
            error.setErrorCode("ERROR " + code);
            error.setErrorMsg(msg);
            FacesContext.getCurrentInstance()
                    .getExternalContext().redirect("error.xhtml");
        } catch (IOException ex) {
            (new LoggHandler()).logErrorMsg("Could not trigger Error by redirecting to error page.", Level.WARNING, ex);
        }
    }

    /**
     * Changed application status
     *
     * @param obj application
     * @return Response object with data if Response status is ok
     */
    public Response changeAppStatus(JsonObject obj) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, STATUS_PATH));
        request = addAuthorizationHeader(request);
        Response response = request.post(Entity.json(obj));
        return validateResponseStatus(response);
    }

    private void addLocaleHeader(Invocation.Builder request) {
        request.header("locale", languageChange.getLanguage());
    }

    private Response sendGetRequest(Invocation.Builder request) {
        try {
            return validateResponseStatus(request.get());
        } catch (Exception ex) {
            triggerError(500, "Remote server issues me think...");
            return null;
        }
    }

    private Response sendPostRequest(Invocation.Builder request, Entity<?> entity) {
        try {
            return validateResponseStatus(request.post(entity));
        } catch (Exception ex) {
            triggerError(500, "Remote server issues me think...");
            return null;
        }
    }

}
