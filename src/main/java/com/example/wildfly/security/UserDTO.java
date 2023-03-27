package com.example.wildfly.security;

import java.util.List;
import java.util.Set;

public record UserDTO(String username, Set<String> role) {
}
