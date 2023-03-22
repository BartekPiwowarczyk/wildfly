package com.example.wildfly.security;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "USER_ROLES")
public class UserRoles implements Serializable {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "ROLE")
    private String role;

//    @ManyToOne
//    @JoinColumn(name="USERNAME", referencedColumnName = "USERNAME", insertable = false, updatable = false)
//    private User user;

    public UserRoles() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
