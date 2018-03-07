/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;

/**
 *
 * @author Evan
 * @author Oscar
 */

public class StatusNameDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private String supportedLanguage;

    public StatusNameDTO() {
    }

    public StatusNameDTO(String name, String supportedLanguage) {
        this.name = name;
        this.supportedLanguage = supportedLanguage;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSupportedLanguage() {
        return supportedLanguage;
    }

    public void setSupportedLanguage(String supportedLanguage) {
        this.supportedLanguage = supportedLanguage;
    }


}
