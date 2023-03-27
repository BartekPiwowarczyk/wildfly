package com.example.wildfly.security;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class UserController implements Serializable {

    Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Inject
    HttpServletRequest request;
    @Inject
    UserService userService;


    private String username = getName();
    private String password;
    private String passwordConfirm;
    private Set<UserRoles> roles;
    private Set<UserRoles> allUserRoles = new HashSet<>();

    @PostConstruct
    public void init() {
        allUserRoles = Arrays.stream(UserRoles.values()).collect(Collectors.toSet());
        LOGGER.info("UserRole : {}", allUserRoles);
    }

    public Set<UserRoles> getUserRoles() {
        return allUserRoles;
    }

    public void setUserRoles(Set<UserRoles> userRoles) {
        this.allUserRoles = userRoles;
    }

    public String getName() {
        if (request!=null) {
            Principal principal = request.getUserPrincipal();
            return principal != null ? principal.getName() : "Nieznany";
        }
        return "???";
    }

    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    public void editUser() {
        LOGGER.info("editUser()");
        User userToUpdate = new User();
        userToUpdate.setUsername(username);
        if(password.equals(passwordConfirm)) {
            userToUpdate.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        } else {
            throw new RuntimeException("Hasła nie są takie same");
        }
        Set<UserRoles> editRoles;
        editRoles = roles;
        LOGGER.info("roles {}", roles);
        userToUpdate.setRoleName(editRoles);
        LOGGER.info("Username: {}, role: {}, id: {}", userToUpdate.getUsername(), userToUpdate.getRoleName());
        userService.editUser(userToUpdate);
        LOGGER.info("Username: {}, role: {}, id: {}", userToUpdate.getUsername(), userToUpdate.getRoleName());
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

    public Set<UserRoles> getAllUserRoles() {
        return allUserRoles;
    }

    public void setAllUserRoles(Set<UserRoles> allUserRoles) {
        this.allUserRoles = allUserRoles;
    }

    public Set<UserRoles> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRoles> roles) {
        this.roles = roles;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
