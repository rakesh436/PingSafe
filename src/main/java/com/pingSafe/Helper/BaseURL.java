package com.pingSafe.Helper;

import java.util.Properties;

public class BaseURL {

    public String getBaseUrl() {
        return PropertiesManager.getInstance().getProperty("Base_URL");
    }
}
