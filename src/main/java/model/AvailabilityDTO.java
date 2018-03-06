
package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Class for sending availabilities from server to client
 * @author Oscar
 */
public class AvailabilityDTO implements Serializable{
    private static final long serialVersionUID = 1L;
    private Date fromDate;
    private Date toDate;
    /**
     * Constructor for DTO
     */
    public AvailabilityDTO() {

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
        return fromDate != null ? new Date(fromDate.getTime()) : null;
    }

    /**
     * Sets the fromDate property
     *
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate != null ? new Date(fromDate.getTime()) : null;
    }

    /**
     * Gets the value of the toDate property
     *
     * @return toDate as Date object
     */
    public Date getToDate() {
        return toDate != null ? new Date(toDate.getTime()) : null;
    }

    /**
     * Sets the toDate property
     *
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = toDate != null ? new Date(toDate.getTime()) : null;
    }

    @Override
    public String toString() {
        return "AvailabilityDTO{" + "fromDate=" + fromDate + ", toDate=" + toDate + '}';
    }
    
}
