package com.example.wildfly.security;

import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class UserController implements Serializable {

    Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    @Inject
    Principal principal;

    @Inject
    UserService userService;

    private String username;
    private String password;
    private String role;



    public String getName() {
        return principal !=null ? principal.getName() : "Nieznany";
    }

    public void addUser() {
        LOGGER.info("Request arrived");
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        List<UserRoles> roles = new ArrayList<>();
        UserRoles userRoles = new UserRoles();
        userRoles.setUsername(username);
        userRoles.setRole(role);
        roles.add(userRoles);
        user.setUserRoles(roles);
        LOGGER.info("username" + username);
        userService.createNewUser(user,userRoles);
        LOGGER.info("create User");
    }

    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
