package com.example.wildfly.security;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Collection;

@NamedQuery(name = "User.findAll",query = "SELECT distinct u from User u join fetch u.roleName r")
@NamedQuery(name = "User.findAllRoles",query = "SELECT r from User u join  u.roleName r")
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD_HASH")
    private String password;


    @ElementCollection
    @CollectionTable(name = "USER_ROLES", joinColumns = {
            @JoinColumn(name = "USERNAME", referencedColumnName = "USERNAME")
    })
    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE")
    private Collection<UserRoles> roleName;

    public User() {
    }

    public Collection<UserRoles> getRoleName() {
        return roleName;
    }

    public void setRoleName(Collection<UserRoles> roleName) {
        this.roleName = roleName;
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
