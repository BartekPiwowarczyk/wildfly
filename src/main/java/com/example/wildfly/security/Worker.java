package com.example.wildfly.security;


import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.jws.soap.SOAPBinding;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class Worker implements Serializable {

    private String nameWorker;
    private String passwordWorker;

    private String passwordConfirm;
    private Set<UserRoles> rolesWorker;
    private Set<UserRoles> possibleRoles;

    @Inject
    UserService userService;

    Logger LOGGER = LoggerFactory.getLogger(Worker.class);

    public void addWorker() {
        LOGGER.info("Request arrived");
        User user = new User();
        user.setUsername(nameWorker);
        user.setPassword(BCrypt.hashpw(passwordWorker, BCrypt.gensalt()));
        Set<UserRoles> rolesAddUser;
        rolesAddUser = rolesWorker;
        user.setRoleName(rolesAddUser);
        LOGGER.info("user send to service" + user);
        userService.createNewUser(user);
        LOGGER.info("created User");
        nameWorker = "";
        rolesWorker = new HashSet<>();
    }

    public void deleteUser(String usernameToDelete) {
        LOGGER.info("usernameToDelete: {}", usernameToDelete);
        userService.deleteUser(usernameToDelete);
    }

    public void editSelectedUser(UserDTO user) {
        LOGGER.info("Edit selected user");
        LOGGER.info("username1: {}",user.username());
        nameWorker = user.username();
        rolesWorker = user.role().stream().map(rolesWorker->UserRoles.valueOf(rolesWorker)).collect(Collectors.toSet());
    }

    public void editUser() {
        LOGGER.info("Request arrived editUser()");
        User userToUpdate = new User();
        userToUpdate.setUsername(nameWorker);
        if(passwordWorker.equals(passwordConfirm)) {
            userToUpdate.setPassword(BCrypt.hashpw(passwordWorker, BCrypt.gensalt()));
        } else {
            throw new RuntimeException("Hasła nie są takie same");
        }
        Set<UserRoles> editRoles;
        editRoles = rolesWorker;
        LOGGER.info("roles {}", rolesWorker);
        userToUpdate.setRoleName(editRoles);
        LOGGER.info("Username: {}, role: {}, id: {}", userToUpdate.getUsername(), userToUpdate.getRoleName());
        userService.editUser(userToUpdate);
        LOGGER.info("Username: {}, role: {}, id: {}", userToUpdate.getUsername(), userToUpdate.getRoleName());
    }


    public String getWorkerName() {
        return nameWorker;
    }

    public void setWorkerName(String workerName) {
        this.nameWorker = workerName;
    }

    public String getPasswordWorker() {
        return passwordWorker;
    }

    public void setPasswordWorker(String passwordWorker) {
        this.passwordWorker = passwordWorker;
    }

    public Set<UserRoles> getRolesWorker() {
        return rolesWorker;
    }

    public void setRolesWorker(Set<UserRoles> rolesWorker) {
        this.rolesWorker = rolesWorker;
    }

    public Set<UserRoles> getPossibleRoles() {
        return possibleRoles;
    }

    public void setPossibleRoles(Set<UserRoles> possibleRoles) {
        this.possibleRoles = possibleRoles;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
}
