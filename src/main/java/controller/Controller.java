/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import datarepresentation.Availability;
import datarepresentation.Competence;
import datarepresentation.CompetenceDTO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author Oscar
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateful
public class Controller {
    private final String BASE_URL = "http://localhost:8080/RecruitmentServ/webresources";
    private final String APPLY_PATH = "apply";
    private final String COMPETENCE_PATH = "competence";
    private final String AVAILABILITY_PATH = "availability";
    private final String LOGIN_PATH = "login";
    private final String TEST_TOKEN_PATH = "testtoken";
    private final Client client = ClientBuilder.newClient();
    private final String AUTHORIZATION_SCHEMA = "Bearer ";
    private String token = "";
    
    public Response login() {
        Response loginResponse = client.target(BASE_URL).path(APPLY_PATH).path(LOGIN_PATH).request().get();
        extractTokenFromLoginResponse(loginResponse);
        return Response.ok().build();
    }
    
    public Response getCompetences() {
        Invocation.Builder request = getRequestToPath(Arrays.asList(APPLY_PATH));
        request = addAuthorizationHeader(request);
        
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

    public Response testToken() {
        return client.target(BASE_URL).path(APPLY_PATH).path(TEST_TOKEN_PATH)
                .request().header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_SCHEMA + token).get();
    }
    
    private void extractTokenFromLoginResponse(Response resp) {
        token = resp.readEntity(String.class);
        System.out.println("CLIENT SERVER TOKEN = " + token);
    }
    
    private Invocation.Builder addAuthorizationHeader(Invocation.Builder target) {
        return target.header(HttpHeaders.AUTHORIZATION, AUTHORIZATION_SCHEMA + token);
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
