/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import datarepresentation.Availability;
import datarepresentation.Competence;
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
import javax.ws.rs.core.Response;
import net.Net;

/**
 *
 * @author Oscar
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Controller {
    private final String BASE_URL = "http://localhost:8080/RecruitmentServ/webresources";
    private final String LOGIN_REGISTER_PATH = "kth.iv1201.recruitmentserv.person";
    private final String APPLY_PATH = "apply";
    private final String APPLICATIONS_PATH = "applications";
    private final String COMPETENCE_PATH = "competence";
    private final String AVAILABILITY_PATH = "availability";
    private final String TEST_TOKEN_PATH = "testtoken";
    private final Client client = ClientBuilder.newClient();
    private final String AUTHORIZATION_SCHEMA = "Bearer ";
    private final String LIST_APPLICATIONS_PATH = "listApplications";
    private final String SEARCH_APPLICATION_PATH = "searchApplication";
    private String token = "";
    private String role = "";
    
    
    
    public Response login(JsonObject json) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(LOGIN_REGISTER_PATH));
        
        Response loginResponse = request.post(Entity.json(json));
        loginResponse.bufferEntity();
        exctractTokenAndRoleFromResponse(loginResponse);
        
        return loginResponse;
    }
    
    public Response getCompetences(String language) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLY_PATH));
        request = addAuthorizationHeader(request);
        request.header("language", language);
        Response response = request.get();
        return validateResponseStatus(response);
    }
    
    public Response listApplications() {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, LIST_APPLICATIONS_PATH));
        request = addAuthorizationHeader(request);
        Response response = request.get();
        return validateResponseStatus(response);
    }

    
    
    public Response getCompetencesForRecruiter(String language) {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, COMPETENCE_PATH));
        request = addAuthorizationHeader(request);
        request.header("language", language);
        Response response = request.get();
        return validateResponseStatus(response);
    }    
    public Response sendCompetences(List<Competence> competenceProfiles) {
        GenericEntity<List<Competence>> entity = new GenericEntity<List<Competence>>(competenceProfiles) {};
        
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLY_PATH, COMPETENCE_PATH));
        request = addAuthorizationHeader(request);
        
        Response response = request.post(Entity.json(entity));
        validateResponseStatus(response);
        
        return response;
    }
    
    public Response sendAvailabilities(List<Availability> availabilities) {
        GenericEntity<List<Availability>> entity = new GenericEntity<List<Availability>>(availabilities) {};
        
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLY_PATH, AVAILABILITY_PATH));
        request = addAuthorizationHeader(request);
        
        Response response = request.post(Entity.json(entity));
        validateResponseStatus(response);
        
        return response;
    }
    public Response searchApplication(JsonObject searchParams) {
        GenericEntity<JsonObject> entity = new GenericEntity<JsonObject>(searchParams) {};
        
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLICATIONS_PATH, SEARCH_APPLICATION_PATH));
        request = addAuthorizationHeader(request);
        
        Response response = request.post(Entity.json(entity));
        validateResponseStatus(response);
        
        return response;
    }
    public Response testToken() {
        return client.target(BASE_URL).path(APPLY_PATH).path(TEST_TOKEN_PATH)
                .request().header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_SCHEMA + token).get();
    }
    
    private void exctractTokenAndRoleFromResponse(Response response) {
        JsonObject json = response.readEntity(JsonObject.class);
        token = json.getString("token", "");
        role = json.getString("role", "");
        System.out.println("CLINET RECEIVED TOKEN: " + token + ", ROLE: " + role);
    }
    
    private Invocation.Builder addAuthorizationHeader(Invocation.Builder target) {
        String tok;
        try {
            tok = Net.token;
        } catch(Exception ex) {
            tok = "";
        }
        
        return target.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_SCHEMA + tok);
    }
    
    private Invocation.Builder getRequestToPath(List<String> paths) {
        WebTarget target = client.target(BASE_URL);
        
        for(String path : paths) {
            target = target.path(path);
        }
                
        return target.request();
    }
    
    private Response validateResponseStatus(Response response) {
        switch(response.getStatus()) {
            case 401:
                System.out.println("401 bsnitch");
                triggerError(401, "BLOCKING YOU BeaCH");
                break;
            case 403:
                System.out.println("403 forbidden");
                triggerError(403, "I FORBID YOU!");
            default:
                System.out.println("defaulting: " + response.getStatus() + ", status: " + response.getStatusInfo());
                break;
        }
        
        return response;
    }
    
    private void triggerError(int code, String msg) {
        try {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(code, msg);
            FacesContext.getCurrentInstance().responseComplete();
        } catch(IOException ex) {
            System.out.println("ERROR CAUGHT : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
