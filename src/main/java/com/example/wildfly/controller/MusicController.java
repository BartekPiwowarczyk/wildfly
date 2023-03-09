package com.example.wildfly.controller;

import com.example.wildfly.model.dto.ArtistDTO;
import com.example.wildfly.model.entity.Artist;
import com.example.wildfly.service.ArtistService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.awt.*;
import java.util.List;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MusicController {

    @Inject
    ArtistService artistService;

    @GET
    @Path("/artists/{name}")
    public Artist getArtistByName(@PathParam("name") String name) {
        return artistService.getArtistByNameWithCriteria(name);
    }

    @GET
    @Path("/artistsDTO/{id}")
    public ArtistDTO getArtistDTOById(@PathParam("id") Long id) {
        return artistService.getArtistDTOById(id);
    }

    @GET
    @Path("/artistsDTO/criteria/{id}")
    public ArtistDTO getArtistDTOFromCriteriaById(@PathParam("id") Long id) {
        return artistService.getArtistDTOForIdWithCriteria(id);
    }
}
