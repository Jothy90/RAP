package org.ygc.rap.object;

import javax.persistence.*;

/**
 * Created by john on 10/25/14.
 */

@Entity
@Table(name = "user")
public class User {

    @Id @GeneratedValue
    @Column(name = "id")
    private Integer id;


    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
