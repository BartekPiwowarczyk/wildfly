package com.example.wildfly.spotify.controller;

import com.example.wildfly.spotify.auth.AuthEndpoint;
import com.example.wildfly.spotify.auth.TokenService;
import com.example.wildfly.spotify.interfaces.SpotifyInterface;
import com.example.wildfly.spotify.model.dto.AlbumSpotify;
import com.example.wildfly.spotify.model.dto.SearchAlbumResponse;
import com.example.wildfly.spotify.service.SpotifyAlbumService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;


@RequestScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/spotify")
//@RegisterRestClient(configKey = "spotify-client")
public class SpotifyEndpoint {

    Logger LOGGER = LoggerFactory.getLogger(SpotifyEndpoint.class);

    @Inject
    @RestClient
    SpotifyInterface spotifyInterface;

    @Inject
    AuthEndpoint authEndpoint;

    @Inject
    SpotifyAlbumService spotifyAlbumService;

    @Inject
    TokenService tokenService;


    @GET
    @Path("/albums/{albumId}")
    public AlbumSpotify getAlbum(@PathParam("albumId") String albumId) {
        if(tokenService.getAuth() == null) {
            LOGGER.info("New Token");
            authEndpoint.authSpotify();
        }
        LOGGER.info("Authorization: "  + tokenService.getToken());
        return  spotifyInterface.getAlbum(tokenService.getToken(), albumId);
        }

    @GET
    @Path("/albums/search")
    public SearchAlbumResponse getAlbumFromSearch(@QueryParam("album") String album) {
        if(tokenService.getAuth() == null) {
            LOGGER.info("New Token");
            authEndpoint.authSpotify();
        }

        LOGGER.info("Authorization: "  + tokenService.getToken());
        return spotifyInterface.getSearch(tokenService.getToken(),album, "album");
    }

    @POST
    @Path("/albums/{albumId}")
    public AlbumSpotify addAlbum(@PathParam("albumId") String albumId) {
        if(tokenService.getAuth() == null) {
            authEndpoint.authSpotify();
        }
        LOGGER.info("Authorization: "  + tokenService.getToken());
        AlbumSpotify albumToSave = spotifyInterface.getAlbum(tokenService.getToken(), albumId);
        spotifyAlbumService.addAlbumSpotifyToDb(albumToSave);
        return  albumToSave;
    }


    }

