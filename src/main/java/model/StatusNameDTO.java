package model;

import java.io.Serializable;

/**
 * DTO for status
 * 
 * @author Emil
 */

public class StatusNameDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String supportedLanguage;
    /**
     * Constructor
     */
    public StatusNameDTO() {
    }
    /**
     * Create status by name and language
     * @param name name of status
     * @param supportedLanguage language of status
     */
    public StatusNameDTO(String name, String supportedLanguage) {
        this.name = name;
        this.supportedLanguage = supportedLanguage;
    }

    /**
     * Returns the name of the status
     * @return name of status
     */
    public String getName() {
        return name;
    }
    /**
     * Sets the name of the status
     * @param name status name
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Get the language of the status
     * @return language of status
     */
    public String getSupportedLanguage() {
        return supportedLanguage;
    }
    /**
     * Set the language of the status
     * @param supportedLanguage status language
     */
    public void setSupportedLanguage(String supportedLanguage) {
        this.supportedLanguage = supportedLanguage;
    }


}
