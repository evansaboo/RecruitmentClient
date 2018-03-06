
package model;

import java.util.Date;

/**
 * Class for sending availabilities from server to client
 * @author Oscar
 */
public class AvailabilityDTO{
    private Date fromDate;
    private Date toDate;
    /**
     * Constructor for DTO
     */
    public AvailabilityDTO() {
        fromDate = new Date();
        toDate = new Date();
    }

   /**
     * Class Constructor
     *
     * @param fDate sets the fromDate property
     * @param tDate sets the toDAte property
     */
    public AvailabilityDTO(Date fDate, Date tDate) {
        this.fromDate = new Date(fDate.getTime());
        this.toDate = new Date(tDate.getTime());
    }

    /**
     * Gets the value of the fromDate property
     *
     * @return fromDate as Date object
     */
    public Date getFromDate() {
        return new Date(fromDate.getTime());
    }

    /**
     * Sets the fromDate property
     *
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = new Date(fromDate.getTime());
    }

    /**
     * Gets the value of the toDate property
     *
     * @return toDate as Date object
     */
    public Date getToDate() {
        return new Date(toDate.getTime());
    }

    /**
     * Sets the toDate property
     *
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = new Date(toDate.getTime());
    }

    @Override
    public String toString() {
        return "AvailabilityDTO{" + "fromDate=" + fromDate + ", toDate=" + toDate + '}';
    }
    
}
