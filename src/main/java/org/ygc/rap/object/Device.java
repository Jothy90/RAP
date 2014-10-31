package org.ygc.rap.object;

import org.ygc.rap.util.RapCommonUtil;

import javax.persistence.*;

/**
 * Created by john on 10/30/14.
 */
@Entity
@Table(name = "device")
public class Device {

    @Id @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "type")
    private  Integer type;

    @Column(name = "low_temp")
    private Integer lowTemp;

    @Column(name = "high_temp")
    private Integer highTemp;

    @Column(name = "temperature")
    private Integer temperature;

    @Column(name = "low_hum")
    private Integer lowHum;

    @Column(name = "high_hum")
    private Integer highHum;

    @Column(name = "humidity")
    private Integer humidity;

    @Column(name = "mask")
    private String mask;


    public void setSensorData(String sensorData){
        temperature= RapCommonUtil.temperatureValue(sensorData);
        humidity= RapCommonUtil.humidityValue(sensorData);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLowTemp() {
        return lowTemp;
    }

    public void setLowTemp(Integer lowTemp) {
        this.lowTemp = lowTemp;
    }

    public Integer getHighTemp() {
        return highTemp;
    }

    public void setHighTemp(Integer highTemp) {
        this.highTemp = highTemp;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getLowHum() {
        return lowHum;
    }

    public void setLowHum(Integer lowHum) {
        this.lowHum = lowHum;
    }

    public Integer getHighHum() {
        return highHum;
    }

    public void setHighHum(Integer highHum) {
        this.highHum = highHum;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

}
