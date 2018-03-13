/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logger;

import java.io.IOException;
import java.io.Serializable;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;

/**
 * LoggHandler class to log error in log files
 *
 * @author Evan
 */
public class LoggHandler implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger("Client_Logger");
    private final String CLIENT_LOGGER_SEVERE_FILE = "Client_Logger_Severe.log";
    private final String CLIENT_LOGGER_WARNING_FILE = "Client_Logger_Warning.log";
    private final String CLIENT_LOGGER_INFO_FILE = "Client_Logger_Info.log";

    private FileHandler fh;

    /**
     * Class Constructor
     */
    public LoggHandler() {

    }

    /**
     * Logs error message into desired file
     *
     * @param message provided costum message
     * @param ex Throwable exception to log
     * @param logLvl Log Level
     */
    public void logErrorMsg(String message, Level logLvl, Exception ex) {
        try {
            String file_name;
            switch (logLvl.getName()) {
                case "SEVERE":
                    file_name = CLIENT_LOGGER_SEVERE_FILE;
                    break;
                case "WARNING":
                    file_name = CLIENT_LOGGER_WARNING_FILE;
                    break;
                case "INFO":
                    file_name = CLIENT_LOGGER_INFO_FILE;
                    break;
                default:
                    file_name = CLIENT_LOGGER_SEVERE_FILE;
                    break;

            }
            fh = new FileHandler(file_name, true);
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            if (ex != null) {
                LOGGER.log(logLvl, message, ex);
            } else {
                LOGGER.log(logLvl, message);

            }

            fh.close();
        } catch (SecurityException | IOException e) {
            java.util.logging.Logger.getLogger(LoggHandler.class.getName()).log(Level.SEVERE, null, e);

        }
    }
}
