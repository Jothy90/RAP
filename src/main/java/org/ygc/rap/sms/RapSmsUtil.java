package org.ygc.rap.sms;

import java.util.logging.Logger;

/**
 * Created by john on 10/30/14.
 */
public class RapSmsUtil {
    private final static Logger LOGGER = Logger.getLogger(RapSmsUtil.class.getName());

    public static String removeRMP(String message) {
        message = message.toLowerCase();
        if (message.startsWith("dia ")) {
            message = message.substring(4);
        } else {
            message = "INVALID";
        }
        return message;
    }
}
