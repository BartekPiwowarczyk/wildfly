package com.example.wildfly.spotify.interfaces;

import com.example.wildfly.spotify.model.dto.AlbumSpotify;
import com.example.wildfly.spotify.model.dto.SearchAlbumResponse;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.*;

@RegisterRestClient(configKey = "spotify-client")
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
}
