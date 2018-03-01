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
import view.ApplicationListing;

@Named(value = "language")
@SessionScoped
public class LanguageChange implements Serializable {

    @Inject
    ApplicationListing al;

    private Locale locale = FacesContext.getCurrentInstance().getApplication().getDefaultLocale();

    /**
     * Returns the current language of the application
     *
     * @return the current locale
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Returns the current locale as of the application as a string
     *
     * @return locale as a string
     */
    public String getLanguage() {
        return locale.getLanguage();
    }

    /**
     * Sets the locale to whatever language was sent as parameter also calls for
     * competences to update
     *
     * @param language A language string sent from the web page
     */
    public void changeLanguage(String language) {
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }

}
