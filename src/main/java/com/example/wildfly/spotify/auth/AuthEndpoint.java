package com.example.wildfly.spotify.auth;

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

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/api")
public class AuthEndpoint {

    @Inject
    TokenService tokenService;

    @Inject
    AuthConfig authConfig;

    @RestClient
    SpotifyAuthInterface spotifyAuthInterface;

    @GET
    @Path("/access_token")
    public SpotifyAuthResponse authSpotify() {
        String authorization = computeBasicHeader();
        SpotifyAuthResponse token = spotifyAuthInterface.getToken(authorization, "client_credentials");
        tokenService.setAuth(token.accessToken());
        return token;
    }

    private String computeBasicHeader() {
        return "Basic " + Base64.getEncoder().encodeToString(
                (authConfig.getClientId() + ":" + authConfig.getClientSecret()).getBytes(StandardCharsets.UTF_8)
        );
    }



}
