package view;

import rest.RestCommunication;
import java.io.Serializable;
import java.util.ResourceBundle;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.ws.rs.core.Response;
/**
 * Takes the input from the login pages and sends them along to the restcommunication which returns a reponse 
 * that is handled and the user redirected.
 * @author Emil
 */
@Named("login")
@SessionScoped
public class Authentication implements Serializable {

    @Inject
    RestCommunication controller;

    private String user;
    private String password;
    private String reguser;
    private String regpassword;
    private String regpassword2;
    private String ssn;
    private String name;
    private String surname;
    private String email;
    private String msgToUser;
    private String loggedon;
    public static String token;
    public static String role;
    JsonProvider provider = JsonProvider.provider();

    /**
     * A randomly generated token for a logged on user.
     *
     * @return a string containing the randomly generated token
     */
    public String getLoggedon() {
        return loggedon;
    }

    /**
     * Returns the entered username.
     *
     * @return username as entered by user
     */
    public String getUser() {
        return user;
    }

    /**
     * Set the username by user logging on.
     *
     * @param user username entered by user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Returns the username entered on registration.
     *
     * @return username entered by user trying to register
     */
    public String getRegUser() {
        return reguser;
    }

    /**
     * Sets the username of a user registering.
     *
     * @param reguser The username entered by registering user
     */
    public void setRegUser(String reguser) {
        this.reguser = reguser;
    }

    /**
     * Returns the social security number of the person registering.
     *
     * @return entered social security number
     */
    public String getSsn() {
        return ssn;
    }

    /**
     * Sets social security number user enters while registering.
     *
     * @param ssn the entered socurity number
     */
    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    /**
     * Returns the registerers first name.
     *
     * @return first name entered
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the first name the user has entered
     *
     * @param name the entered first name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the registerers entered surname.
     *
     * @return surname entered
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname the user has entered.
     *
     * @param surname the entered surname.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Returns the registerers email address.
     *
     * @return email entered entered.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address the user has entered.
     *
     * @param email the entered email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns the registerers entered surname.
     *
     * @return surname entered
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password for a user logging on.
     *
     * @param password an entered password from person logging on.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Returns the registerers entered password.
     *
     * @return password entered
     */
    public String getRegPassword() {
        return regpassword;
    }

    /**
     * Sets the password for a user registering.
     *
     * @param regpassword an entered password from regiserer
     */
    public void setRegPassword(String regpassword) {
        this.regpassword = regpassword;
    }
    /**
     * Returns the registerers entered password.
     *
     * @return password entered
     */
    public String getRegPassword2() {
        return regpassword2;
    }

    /**
     * Sets the password for a user registering.
     *
     * @param regpassword an entered password from regiserer
     */
    public void setRegPassword2(String regpassword2) {
        this.regpassword2 = regpassword2;
    }

    /**
     * Get errormessage to display for user and then reset it.
     *
     * @return the errormessage to display for user
     */
    public String getMsgToUser() {
        String s = msgToUser;
        msgToUser = null;
        return s;
    }

    public String getRole() {
        return role;
    }

    public ResourceBundle getLangProperties() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().evaluateExpressionGet(context, "#{msg}", ResourceBundle.class);
    }

    /**
     * Creates a JsonObject of the user credentials and sends it to authenticate
     * the user.
     *
     * @return Returns the result of the authentication and an errormessage if
     * it fails.
     */
    public String login() {
        try {
            JsonObject job;

            job = provider.createObjectBuilder()
                    .add("username", user)
                    .add("password", password).build();

            Response authResponse = controller.login(job);
            return validateAndExtractAuthResponse(authResponse, "errorMsg_authFailed");

        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    /**
     * Sends user credentials to the toServ method and check from recieved
     * result if sucessfull registration sets error message on fail
     *
     * @return redirect to start page on sucess
     */
    public String register() {
        if(!regpassword.equals(regpassword2)){
            msgToUser = getLangProperty("pmatch");
        }else{
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
            return validateAndExtractAuthResponse(authResponse, "errorMsg_uTaken");

        } catch (Exception e) {
            e.printStackTrace();
        }
        }
        return "";
    }

    /**
     * Sends a request to the server to log out the user and then removes users
     * id. Also sends a message to the user confirming logout
     *
     * @return returns a redirect message
     */
    public String logout() {
        controller.logout();

        return "login?faces-redirect=true";
    }

    /**
     * Validates the authorization reponse and redirects the user on success and
     * setting a token. Also redirects the user based on the result.
     *
     * @param response The reseult of the authentication
     * @param authMsg An errormessage based on the type of request
     * @return
     */
    private String validateAndExtractAuthResponse(Response response, String authMsg) {
        JsonObject json = response.readEntity(JsonObject.class);
        String error = json.getString("error", "");

        if (error.isEmpty()) {
            loggedon = json.getString("token", "");
            token = loggedon;
            role = json.getString("role", "");
            user = json.getString("username");
            msgToUser = getLangProperty("logoutMsg");
            return roleRedirect();
        } else {
            msgToUser = getLangProperty(authMsg);
            return "login?faces-redirect=true";
        }
    }

    private String roleRedirect() {
        if (role.equals("Applicant")) {
            return "apply?faces-redirect=true";
        } else if (role.equals("Recruiter")) {
            return "applications?faces-redirect=true";
        }

        return "index?faces-redirect=true";
    }

    public String getLangProperty(String property) {
        return getLangProperties().getString(property);
    }
}
