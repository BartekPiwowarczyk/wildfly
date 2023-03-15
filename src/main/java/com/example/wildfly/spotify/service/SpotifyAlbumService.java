package com.example.wildfly.spotify.service;

import com.example.wildfly.model.entity.Album;
import com.example.wildfly.spotify.model.dto.AlbumSpotify;
import com.example.wildfly.spotify.model.dto.SearchAlbumResponse;
import com.example.wildfly.spotify.model.dto.TrackSpotify;
import com.example.wildfly.spotify.model.mapper.SpotifyAlbumMapper;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.util.List;


@ApplicationScoped
public class SpotifyAlbumService {

    Logger LOGGER = LoggerFactory.getLogger(SpotifyAlbumService.class);
    @PersistenceContext
    EntityManager em;
    @Inject
    SpotifyAlbumMapper spotifyAlbumMapper;

    @Transactional
    public AlbumSpotify addAlbumSpotifyToDb(AlbumSpotify albumSpotify) {
        LOGGER.info("create new Album");
        Album album = spotifyAlbumMapper.fromAlbumSpotifyResponse(albumSpotify);
        LOGGER.info("Album created, artistId:{},title:{},albumsongs:{}", album.getArtistId(), album.getTitle(), album.getAlbumSongs());
        em.persist(album);
        LOGGER.info("Album added to db");
        return albumSpotify;
    }


}
