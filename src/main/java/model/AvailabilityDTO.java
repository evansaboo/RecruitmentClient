package model;

import java.io.Serializable;
import java.util.Date;

/**
 * Class for sending availabilities from server to client
 *
 * @author Oscar
 */
public class AvailabilityDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date fromDate;
    private Date toDate;
    /**
     * Constructor for DTO
     */
    
    LanguageChange lc = new LanguageChange();
    
    /**
     * Contructor
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
        return lc.parseDateAfterLocale(fromDate);
    }

    /**
     * Sets the fromDate property
     *
     * @param fromDate the fromDate to set
     */
    public void setFromDate(Date fromDate) {
        this.fromDate = lc.parseDateAfterLocale(fromDate);
    }

    /**
     * Gets the value of the toDate property
     *
     * @return toDate as Date object
     */
    public Date getToDate() {
        return lc.parseDateAfterLocale(toDate);
    }

    /**
     * Sets the toDate property
     *
     * @param toDate the toDate to set
     */
    public void setToDate(Date toDate) {
        this.toDate = lc.parseDateAfterLocale(toDate);
    }
    
    /**
     * Converts object of this class to string
     * @return string of object
     */
    @Override
    public String toString() {
        return "AvailabilityDTO{" + "fromDate=" + fromDate + ", toDate=" + toDate + '}';
    }

}
