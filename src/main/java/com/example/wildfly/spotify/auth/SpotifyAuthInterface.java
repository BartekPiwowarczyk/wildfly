package com.example.wildfly.spotify.auth;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient(baseUri = "https://accounts.spotify.com")
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