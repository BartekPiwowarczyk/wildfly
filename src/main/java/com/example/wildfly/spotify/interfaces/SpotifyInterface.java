package com.example.wildfly.spotify.interfaces;

import com.example.wildfly.spotify.model.dto.AlbumSpotify;
import com.example.wildfly.spotify.model.dto.SearchAlbumResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import jakarta.ws.rs.*;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(baseUri = "https://api.spotify.com/v1")
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
