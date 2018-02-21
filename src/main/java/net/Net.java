package net;

import controller.Controller;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.ws.rs.core.Response;

@Named("login")
@SessionScoped
public class Net implements Serializable {

    @Inject Controller controller;
    
    private String user;
    private String password;
    private String reguser;
    private String regpassword;
    private String ssn;
    private String name;
    private String surname;
    private String email;
    private String authenticated;
    private String loggedon;
    public static String token;
    public static String role;
    
    public String getLoggedon() {
        return loggedon;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRegUser() {
        return reguser;
    }

    public void setRegUser(String reguser) {
        this.reguser = reguser;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRegPassword() {
        return regpassword;
    }

    public void setRegPassword(String regpassword) {
        this.regpassword = regpassword;
    }

    public String getAuthenticated() {
        String s = authenticated;
        authenticated = "";
        return s;
    }

    public String toServ() {
        try {
            JsonProvider provider = JsonProvider.provider();
            JsonObject job;

            job = provider.createObjectBuilder()
                    .add("type", "login")
                    .add("username", user)
                    .add("password", password).build();
            
            return login(job, "Authentication failed");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    /**
     * Sends user credentials to the toServ method and check from recieved result if sucessfull registration
     * sets error message on fail 
     * @return redirect to start page on sucess
     */
    public String register() {
        try {
            JsonObject job;
            job = provider.createObjectBuilder()
                    .add("type", "register")
                    .add("name", name)
                    .add("surname", surname)
                    .add("ssn", ssn)
                    .add("email", email)
                    .add("password", regpassword)
                    .add("username", reguser).build();            
            return login(job, "Username taken");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * Sends a request to the server to log out the user and then removes users id.
     * Also sends a message to the user confirming logout
     * @return returns a redirect message
     */
    public String logout() {
        JsonObject job;
        job = provider.createObjectBuilder()
                .add("type", "logout")
                .add("uid", loggedon).build();
        toServ(job);
        loggedon="";
        authenticated="Logged out, goodbye!";
        return "login?faces-redirect=true";
    }
    /**
     * Sends a JsonObject using REST to the server which will handle it.
     * @param job   a JsonObject that contains either a login/register/logout request and user credentials
     * @return  returns a string confirming or denying that the action was sucessful
     */
    public String toServ(JsonObject job) {
        try {
            Client client = ClientBuilder.newClient();
            String s = client.target("http://localhost:8080/RecruitmentServ/webresources/kth.iv1201.recruitmentserv.person")
                    .request()
                    .post(Entity.entity(job, MediaType.APPLICATION_JSON), String.class);
            return s;

        } catch (Exception e) {
            e.printStackTrace();
            return "invalid";
        }
    }

    private String login(JsonObject job, String authMsg) {
        System.out.println("BEROFRE CONTROLLER");
        Response response = controller.login(job);
        System.out.println("AFTER CONTROLLER");
        JsonObject json = response.readEntity(JsonObject.class);
        String error = json.getString("error", "");

        if (error.isEmpty()) {
            loggedon = json.getString("token", "");
            token = loggedon;
            role = json.getString("role", "");
            return "index?faces-redirect=true";
        } else {
            authenticated = authMsg;
            return "login?faces-redirect=true";
        }
    }
    
}
