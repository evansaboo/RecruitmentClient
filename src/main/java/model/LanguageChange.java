/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 * Changes the language of the web application
 *
 * @author Emil
 */
@Named(value = "language")
@SessionScoped
public class LanguageChange implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * Returns text for a property based on language
     *
     * @return text for certain property
     */
    public ResourceBundle getLangProperties() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().setLocale(locale);
        return context.getApplication().evaluateExpressionGet(context, "#{msg}", ResourceBundle.class);
    }

    /**
     * Returns text based on current language for a certain property
     *
     * @param property property to get message for
     * @return text for entered property
     */
    public String getLangProperty(String property) {
        return getLangProperties().getString(property);
    }
    
    /**
     * Parses date to current locale. return null if given date is null
     * @param date given date
     * @return parsed date, else null
     */
    public Date parseDateAfterLocale(Date date) {

        Locale lc = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        if (date != null) {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, lc);
            Date tempdate = null;
            try {
                tempdate = df.parse(df.format(date));
            } catch (ParseException ex) {
                Logger.getLogger(AvailabilityDTO.class.getName()).log(Level.SEVERE, null, ex);
            }

            return tempdate;
        } else {
            return null;
        }
    }
}
