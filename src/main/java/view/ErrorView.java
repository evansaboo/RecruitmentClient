/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * Handles the error views.
 *
 * @author Evan
 */
@Named("errorView")
@SessionScoped
public class ErrorView implements Serializable {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String errorMsg;
    /**
     * Returns the errorcode for the error
     * @return errorcode
     */
    public String getErrorCode() {
        return errorCode;
    }
    /**
     * Sets the errorcode
     * @param errorCode code for error
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    /**
     * Returns message for error
     * @return error message
     */
    public String getErrorMsg() {
        return errorMsg;
    }
    /**
     * Sets the error message
     * @param errorMsg message for error
     */
    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }   
}
