/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Locale;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import net.ApplicationListing;

@Named(value = "language")
@SessionScoped
public class LanguageChange implements Serializable {
    @Inject
    ApplicationListing al;
    
    private Locale locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();
    
    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void changeLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(new Locale(language));
        al.updateCompetences(language);
    }

}
