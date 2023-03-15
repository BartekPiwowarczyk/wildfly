package com.example.wildfly.controller;

import com.example.wildfly.model.dto.AlbumDTO;
import com.example.wildfly.model.dto.AlbumDTOO;
import com.example.wildfly.model.dto.ArtistDTO;
import com.example.wildfly.model.dto.SongDTO;
import com.example.wildfly.model.entity.Album;
import com.example.wildfly.model.entity.Artist;
import com.example.wildfly.service.AlbumService;
import com.example.wildfly.service.ArtistService;
import com.example.wildfly.service.SongService;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.util.List;

@ApplicationScoped
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class MusicController {

    Logger LOGGER = LoggerFactory.getLogger(MusicController.class);
    @Inject
    ArtistService artistService;

    @Inject
    SongService songService;

    @Inject
    AlbumService albumService;


//    @GET
//    @Path("/artists/{name}")
//    public String getArtistByName(@PathParam("name") String name) {
//        artistService.getArtistByNameWithCriteria(name);
//        return "artist";
//    }

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

    @GET
    @Path("/albums/artist/{id}")
    public List<AlbumDTOO> getAlbumsByArtistIdDTOs(@PathParam("id") Long id){
        LOGGER.info("Request arrived");
        try {
            return albumService.getAlbumsByArtistId(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/albumsDTO/criteria")
    public List<AlbumDTOO> getAllAlbumsDTO() {
        LOGGER.info("Request arrived");
        try {
            return albumService.getAllAlbumDTOOrderByAlbumTitle();
        } catch (Exception e) {
            LOGGER.info("Exception in MusicController");
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/albumsDTO/criteria/{id}")
    public AlbumDTOO getAlbumDTOById(@PathParam("id") Long id) {
        LOGGER.info("Request arrived with param: {}", id);
        try {
            return albumService.getAlbumDTOByIdWithCriteria(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }

//ten
    @GET
    @Path("/albums/criteria")
    public List<Album> getAllAlbums() {
        LOGGER.info("Request arrived");
        try {
            return albumService.getAllAlbum();
        } catch (Exception e) {
            LOGGER.info("Exception in MusicController");
            throw new NotFoundException();
        }
    }


    @GET
    @Path("/albums/criteria/{id}")
    public Album getAlbumById(@PathParam("id") Long id) {
        LOGGER.info("Request arrived with param: {}", id);
        try {
            return albumService.getAlbumByIdWithCriteria(id);
        } catch (Exception e) {
            LOGGER.info("Exception in MusicController");
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/albumsDTO/transform")
    public List<AlbumDTO> getAlbumDTOListByIdWithResultTransform() {
        LOGGER.info("Request arrived");
        try {
            return albumService.getAlbumDTOOListWithResultTransform();
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }
    @GET
    @Path("/albumsDTO/transform/{id}")
    public AlbumDTO getAlbumDTOByIdWithResultTransform(@PathParam("id") Long id) {
        LOGGER.info("Request arrived with param: {}", id);
        try {
            return albumService.getAlbumDTOOWithResultTransform(id);
        } catch (Exception e) {
            throw new NotFoundException();
        }
    }


    @PUT
    @Path("/albums")
    public Response createNewAlbum(AlbumDTO albumDTO) {
        try {
            albumService.createNewAlbum(albumDTO);
            return Response.status(201).entity(albumDTO).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }

    @POST
    @Path("/artists")
    public Response createNewArtist(ArtistDTO artistDTO) {
        try {
            artistService.createNewArtist(artistDTO);
            return Response.status(201).entity(artistDTO).build();
        } catch (Exception e) {
            return Response.status(404).build();
        }
    }

    @GET
    @Path("/songs/{id}")
    public SongDTO getSongDTOById(@PathParam("id") Long id) {
        return songService.getSongDTOByIdWithCriteria(id);
    }
}
