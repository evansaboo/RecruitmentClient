/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datarepresentation;

import java.util.Date;

/**
 *
 * @author Oscar
 */
public class Availability {
    private Date fromDate;
    private Date toDate;

    public Availability() {
    }

   /**
     * Class Constructor
     *
     * @param fromDate sets the fromDate property
     * @param toDate sets the toDAte property
     */
    public Availability(Date fromDate, Date toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    /**
     * Gets the value of the fromDate property
     *
     * @return fromDate as Date object
     */
    public Date getFromDate() {
        return fromDate;
    }

    /**
     * Sets the fromDate property
     *
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * Gets the value of the toDate property
     *
     * @return toDate as Date object
     */
    public Date getToDate() {
        return toDate;
    }

    /**
     * Sets the toDate property
     *
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    
}
