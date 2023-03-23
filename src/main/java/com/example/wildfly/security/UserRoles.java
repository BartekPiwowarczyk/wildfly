package com.example.wildfly.security;

import jakarta.persistence.*;

import java.io.Serializable;

public enum UserRoles implements Serializable {
    LOGGED_USER,
    ADMIN
}
