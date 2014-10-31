package org.ygc.rap.object;

import javax.persistence.*;

/**
 * Created by john on 10/31/14.
 */
@Entity
@Table(name = "deviceAccess")
public class DeviceAccess {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "mask")
    private String userMask;

    @Column(name = "device_id")
    private int deviceId;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMask() {
        return userMask;
    }

    public void setUserMask(String userMask) {
        this.userMask = userMask;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }
}