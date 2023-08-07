package com.pingSafe.Helper;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class PropertiesManager {

    private static final Logger log = Logger.getLogger("PropertiesManager");
    private static PropertiesManager instance = null;
    private Properties properties = null;

    /**
     * Creates a singleton instance
     *
     * @return
     */
    public static synchronized PropertiesManager getInstance() {
        if (instance == null) {
            instance = new PropertiesManager();
            instance.loadData();
        }
        return instance;
    }

    /** Retrieves all configuration data and assign to related fields. */
    private PropertiesManager loadData() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
        } catch (IOException e) {
            log.info(e.getMessage());
        }
        return this;
    }

    /**
     * Returns value of the key provided
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        String value = System.getProperty(key) == null ? properties.getProperty(key) : System.getProperty(key);
        return value.trim();
    }

}
