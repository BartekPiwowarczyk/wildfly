package com.example.wildfly.security;

import com.example.wildfly.model.entity.AlbumSong;
import jakarta.inject.Named;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedQuery(name = "User.findAll",query = "SELECT distinct u from User u join fetch u.userRoles r")
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD_HASH")
    private String password;

    @OneToMany
    @JoinColumn(name="USERNAME", referencedColumnName = "USERNAME")
//    @JoinTable(name="USER_ROLES",joinColumns = @JoinColumn(name="ROLE"))
    private List<UserRoles> userRoles;

    public User() {
    }

    public List<UserRoles> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRoles> userRoles) {
        this.userRoles = userRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
