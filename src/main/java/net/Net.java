/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Evan
 */
@Named("login")
@SessionScoped
public class Net implements Serializable {

    private String user;
    private String password;
    private String reguser;
    private String regpassword;
    private String ssn;
    private String name;
    private String surname;
    private String email;

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

    public void test() {
        System.out.println("client test");
        Client client = ClientBuilder.newClient();
        client.target("http://localhost:8080/RecruitmentServ/webresources/kth.iv1201.recruitmentserv.availability")
                .request()
                .get(String.class);
    }

    public void toServ() {
        try {
            JsonProvider provider = JsonProvider.provider();
            JsonObject job;
            
            job = provider.createObjectBuilder()
                    .add("type", "login")
                    .add("username", user)
                    .add("password", password).build();
            
            Client client = ClientBuilder.newClient();
            String s = client.target("http://localhost:8080/RecruitmentServ/webresources/kth.iv1201.recruitmentserv.person")
                    .request()
                    .post(Entity.entity(job, MediaType.APPLICATION_JSON), String.class);
            System.out.println("From serv " + s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void register() {
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
            Client client = ClientBuilder.newClient();
            String s = client.target("http://localhost:8080/RecruitmentServ/webresources/kth.iv1201.recruitmentserv.person")
                    .request()
                    .post(Entity.entity(job, MediaType.APPLICATION_JSON), String.class);
            System.out.println("From serv" + s);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
