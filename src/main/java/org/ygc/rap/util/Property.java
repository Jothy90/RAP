package org.ygc.rap.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Created by john on 10/30/14.
 */
public class Property {
    public static final String BUNDLE_NAME = "org.ygc.rap.rap";
    private static ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

    /**
     *
     * @param key
     * @return
     */
    public static String getValue(final String key) {
        try {
            return resourceBundle.getString(key);
        } catch (MissingResourceException e) {
            return "Error";
        }
    }
}
