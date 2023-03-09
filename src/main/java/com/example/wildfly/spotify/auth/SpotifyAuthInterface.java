package com.example.wildfly.spotify.auth;


import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(configKey = "token-client")
@Path("/api")
public interface SpotifyAuthInterface {

    @Path("/token")
    @POST
    @Consumes("application/x-www-form-urlencoded")
    SpotifyAuthResponse getToken(
            @HeaderParam("Authorization") String authorization,
            @FormParam("grant_type") String grantType
    );

}