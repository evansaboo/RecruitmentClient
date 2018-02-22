package view;

import rest.RestCommunication;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.ws.rs.core.Response;

@Named("login")
@SessionScoped
public class Authentication implements Serializable {

    @Inject RestCommunication controller;
    
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
    JsonProvider provider = JsonProvider.provider();
    
    /**
     *  A randomly generated token for a logged on user.
     * @return a string containing the randomly generated token
     */
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
    /**
     * Creates a JsonObject of the user credentials and sends it to authenticate the user.
     * @return  Returns the result of the authentication and an errormessage if it fails. 
     */
    public String login() {
        try {
            JsonObject job;

            job = provider.createObjectBuilder()
                    .add("username", user)
                    .add("password", password).build();
            
            Response authResponse = controller.login(job);
            return validateAndExtractAuthResponse(authResponse, "Authentication failed");

        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
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
                    .add("name", name)
                    .add("surname", surname)
                    .add("ssn", ssn)
                    .add("email", email)
                    .add("password", regpassword)
                    .add("username", reguser).build();            
            
            Response authResponse = controller.register(job);
            return validateAndExtractAuthResponse(authResponse, "Username taken");
            
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
        controller.logout();
        loggedon="";
        authenticated="Logged out, goodbye!";
        return "login?faces-redirect=true";
    }
    /**
     * Validates the authorization reponse and redirects the user on success and setting a token.
     * Also redirects the user based on the result.
     * @param response  The reseult of the authentication
     * @param authMsg   An errormessage based on the type of request
     * @return 
     */
    private String validateAndExtractAuthResponse(Response response, String authMsg) {
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
