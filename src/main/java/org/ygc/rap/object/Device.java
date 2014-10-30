package org.ygc.rap.object;

import javax.persistence.*;

/**
 * Created by john on 10/30/14.
 */
@Entity
@Table(name = "device")
public class Device {


    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private int userId;


    @Column(name = "type")
    private  Integer type;

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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLow() {
        return low;
    }

    public void setLow(Integer low) {
        this.low = low;
    }

    public Integer getHigh() {
        return high;
    }

    public void setHigh(Integer high) {
        this.high = high;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    @Column(name = "low")

    private Integer low;

    @Column(name = "high")
    private Integer high;

    @Id
    @GeneratedValue
    @Column(name = "mask")
    private String mask;

}
