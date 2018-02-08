/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Evan
 */
@Named("applicationListing")
@SessionScoped
public class ApplicationListing implements Serializable{
    private String date;
    private String timeFrom;
    private String timeTo;
    private String competence;
    private String firstname;
    
    public void getDate(String date){
        this.date = date;
    }

    public String setDate(){
        return date;
    }

    public void getTimeFrom(String timeFrom){
        this.timeFrom = timeFrom;
    }

    public String setTimeFrom(){
        return timeFrom;
    }

    public void getTimeTo(String timeTo){
        this.timeTo = timeTo;
    }
    
    public String setTimeTo(){
        return timeTo;
    }
    
    public void getCompetence(String competence){
        this.competence = competence;
    }
    

    
}
