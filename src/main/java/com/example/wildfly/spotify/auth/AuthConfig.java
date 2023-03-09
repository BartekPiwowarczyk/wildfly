package com.example.wildfly.spotify.auth;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import java.io.Serializable;

@ApplicationScoped
public class AuthConfig implements Serializable {


    @ConfigProperty(name = "spotify.clientId")private String clientId;
    @ConfigProperty(name = "spotify.clientSecret") private String clientSecret;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }
}
