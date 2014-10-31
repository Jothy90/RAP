package org.ygc.rap.util;

/**
 * Created by john on 10/31/14.
 */
public class RapCommonUtil {
    public static int temperatureValue(String sensorData) {
        sensorData=sensorData.toLowerCase();
        String[] tem = sensorData.split(";");
        return Integer.parseInt(tem[0].replace("t:", ""));
    }

    public static int humidityValue(String sensorData) {
        sensorData=sensorData.toLowerCase();
        String[] tem = sensorData.split(";");
        return Integer.parseInt(tem[1].replace("m:", ""));
    }
}
