package com.example.wildfly.spotify.service;

import com.example.wildfly.spotify.auth.AuthEndpoint;
import com.example.wildfly.spotify.auth.TokenService;
import com.example.wildfly.spotify.controller.SpotifyEndpoint;
import com.example.wildfly.spotify.model.dto.SearchAlbumResponse;
import com.example.wildfly.spotify.model.dto.TrackSpotify;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class SpotifyServiceFacelets implements Serializable {

    Logger LOGGER = LoggerFactory.getLogger(SpotifyEndpoint.class);
  @Inject
  SpotifyEndpoint spotifyEndpoint;

    @Inject
    AuthEndpoint authEndpoint;

    @Inject
    TokenService tokenService;
    private String albumName="";
    private List<TrackSpotify> allTrack = new ArrayList<>();

    private String searchKeyUpText="";

    public boolean isAlbumNameBlank() {
        LOGGER.info("isPresent: " + albumName);
       return albumName.isBlank() ? true:false;
    }
    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {
        this.albumName = albumName;
    }

    public List<TrackSpotify> getAllTrack() {
        return allTrack;
    }

    public void setAllTrack(List<TrackSpotify> allTrack) {
        this.allTrack = allTrack;
    }


    public String getSearchKeyUpText() {
        return searchKeyUpText;
    }

    public void setSearchKeyUpText(String searchKeyUpText) {
        this.searchKeyUpText = searchKeyUpText;
    }

    public List<TrackSpotify> getAlbumTracks(String albumId) {
        if(tokenService.getAuth() == null) {
            LOGGER.info("New Token");
            authEndpoint.authSpotify();
        }
        List<TrackSpotify> tracks = spotifyEndpoint.getAlbum(albumId).tracks().items();
        return tracks;
    }


    @Produces(MediaType.APPLICATION_JSON)
    public void getAlbumTracksAfterSearch() {
        if(tokenService.getAuth() == null) {
            LOGGER.info("New Token");
            authEndpoint.authSpotify();
        }
        LOGGER.info("jestem");
        SearchAlbumResponse res = spotifyEndpoint.getAlbumFromSearch(albumName);

        LOGGER.info("res " + res);
        String albumId= res.albums().items().stream().findFirst().map(albumSpotify -> albumSpotify.id()).orElseThrow(()->new NotFoundException("Brak albumu"));
        LOGGER.info("tutaj " + albumId);
        List<TrackSpotify> items = getAlbumTracks(albumId);
        LOGGER.info("Tracks " + items);
        allTrack=items;
    }

    public List<TrackSpotify> filterTracks() {
        List<TrackSpotify> trackForFilter = allTrack.stream().filter(trackSpotify -> trackSpotify.name().contains(searchKeyUpText)).collect(Collectors.toList());
        LOGGER.info("Filter allTrack " + allTrack);
        return trackForFilter;
    }
}
