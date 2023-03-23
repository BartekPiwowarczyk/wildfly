package com.example.wildfly.security;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.ejb.EJBContext;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.Column;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.ws.rs.core.SecurityContext;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class UserController implements Serializable {

    Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Inject
    HttpServletRequest request;
    @Inject
    UserService userService;

    private String username;
    private String password;
    private String role;
    private List<String> roles;
    private User user;
    private List<UserDTO> userList = new ArrayList<>();

    @PostConstruct
    public void init() {

        LOGGER.info("UserList : {}", userList);


    }
    public String getName() {
        Principal principal = request.getUserPrincipal();
        return principal != null ? principal.getName() : "Nieznany";
    }

    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    public void addUser() {
        LOGGER.info("Request arrived");
        User user = new User();
        user.setUsername(username);
        user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        List<UserRoles> roles = new ArrayList<>();
        roles.add(UserRoles.valueOf(role));
        user.setRoleName(roles);
        LOGGER.info("user send to service" + user);
        userService.createNewUser(user);
        LOGGER.info("created User");
    }

    public String goPageEditUser() {
        user = userService.getUser(request.getUserPrincipal().getName());
        username = user.getUsername();
        password = user.getPassword();
        roles =user.getRoleName().stream().map(userRoles -> userRoles.name()).collect(Collectors.toList());
        role=user.getRoleName().stream().map(userRoles -> userRoles.name()).findFirst().get();
        return "secure/editUser.xhtml";
    }

    public void editUser() {
        LOGGER.info("Request arrived");
        String id = username;
        User userToUpdate  = new User();
        userToUpdate.setUsername(username);
        userToUpdate.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
        List<UserRoles> roles = new ArrayList<>();
        roles.add(UserRoles.valueOf(role));
        userToUpdate.setRoleName(roles);
        LOGGER.info("Username: {}, role: {}, id: {}",userToUpdate.getUsername(),userToUpdate.getRoleName(),id);
        userService.editUser(userToUpdate,id);
        LOGGER.info("Username: {}, role: {}, id: {}",userToUpdate.getUsername(),userToUpdate.getRoleName(),id);

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
