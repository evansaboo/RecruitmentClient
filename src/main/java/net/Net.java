/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private String script;
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
    

    public String getScript() {
        return script;
    }
    
    public String getAuthenticated(){
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
        return "";
    }

    public String register() {
        try {
            JsonProvider provider = JsonProvider.provider();
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
