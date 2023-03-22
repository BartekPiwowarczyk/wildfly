package com.example.wildfly.security;

import java.util.List;

public record UserDTO(String username, List<String> role) {
}
