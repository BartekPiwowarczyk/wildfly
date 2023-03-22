package com.example.wildfly.security;


import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Collectors;

@ApplicationScoped
public class UserMapper {

    Logger LOGGER = LoggerFactory.getLogger(UserMapper.class);
    public UserDTO fromUser(User user) {
        return new UserDTO(user.getUsername(), user.getUserRoles().stream().map(userRoles -> userRoles.getRole()).collect(Collectors.toList()));

    }


}
