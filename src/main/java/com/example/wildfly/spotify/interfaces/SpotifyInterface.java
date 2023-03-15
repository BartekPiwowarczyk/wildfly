package com.example.wildfly.spotify.interfaces;

import com.example.wildfly.spotify.model.dto.AlbumSpotify;
import com.example.wildfly.spotify.model.dto.SearchAlbumResponse;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.*;

import java.io.Serializable;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "spotifyApi")
public interface SpotifyInterface {
    @Path("/albums/{name}")
    @GET
    AlbumSpotify getAlbum(
            @HeaderParam("Authorization") String authorization,
            @PathParam("name") String name
    );

    @Path("/search")
    @GET
    SearchAlbumResponse getSearch(
            @HeaderParam("Authorization") String authorization,
            @QueryParam("q") String search,
            @QueryParam("type") String type
    );

    @Path("/albums/{id}/tracks")
    @GET
    AlbumSpotify.TracksSpotify getAlbumTracks(
            @HeaderParam("Authorization") String authorization,
            @PathParam("id") String id
    );
}
