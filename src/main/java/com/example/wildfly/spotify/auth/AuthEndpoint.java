package com.example.wildfly.spotify.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.lifecycle.ClientWindowScoped;
import jakarta.inject.Inject;
import jakarta.websocket.ClientEndpoint;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api")
public class AuthEndpoint {


    Logger LOGGER = LoggerFactory.getLogger(AuthEndpoint.class);

    @Inject
    TokenService tokenService;

    @Inject
    AuthConfig authConfig;

    @Inject
    @RestClient
    SpotifyAuthInterface spotifyAuthInterface;

    @GET
    @Path("/access_token")
    public SpotifyAuthResponse authSpotify() {
        String authorization = computeBasicHeader();
        LOGGER.info("Here you are! Authorization " + authorization);
        SpotifyAuthResponse token = spotifyAuthInterface.getToken(authorization, "client_credentials");
        LOGGER.info("Masz ten token?");
        tokenService.setAuth(token.accessToken());
        return token;
    }

    private String computeBasicHeader() {
        return "Basic " + Base64.getEncoder().encodeToString(
                (authConfig.getClientId() +":"+ authConfig.getClientSecret()).getBytes(StandardCharsets.UTF_8)
        );
    }

}
