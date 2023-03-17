package com.example.wildfly.spotify.service;

import com.example.wildfly.spotify.auth.AuthEndpoint;
import com.example.wildfly.spotify.auth.TokenService;
import com.example.wildfly.spotify.controller.SpotifyEndpoint;
import com.example.wildfly.spotify.model.dto.AlbumSpotify;
import com.example.wildfly.spotify.model.dto.SearchAlbumResponse;
import com.example.wildfly.spotify.model.dto.TrackSpotify;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.view.ViewScoped;
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
//<f:ajax execute="searchAlbumInputKeyUp" render=":albums searchAlbumInputKeyUp"
//        event="keyup" listener="#{spotifyServiceFacelets.filterAlbums()}"
//        delay="500"/>
@Named
@ViewScoped
public class SpotifyServiceFacelets implements Serializable {

    Logger LOGGER = LoggerFactory.getLogger(SpotifyEndpoint.class);
  @Inject
  SpotifyEndpoint spotifyEndpoint;

    @Inject
    AuthEndpoint authEndpoint;

    @Inject
    TokenService tokenService;
    private String albumName="";
    private List<AlbumSpotify> albums = new ArrayList<>();
    private List<TrackSpotify> allTrack = new ArrayList<>();

    private String searchKeyUpText="";
    private String searchAlbum="";
    private String albumId="";

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public boolean isAlbumNameBlank() {
        LOGGER.info("isPresent: " + albumName);
       return albumName.isBlank() ? true:false;
    }

    public String getSearchAlbum() {
        return searchAlbum;
    }

    public void setSearchAlbum(String searchAlbum) {
        this.searchAlbum = searchAlbum;
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

    public List<AlbumSpotify> getAlbums() {
        return albums;
    }

    public void setAlbums(List<AlbumSpotify> albums) {
        this.albums = albums;
    }

    public String getSearchKeyUpText() {
        return searchKeyUpText;
    }

    public void setSearchKeyUpText(String searchKeyUpText) {
        this.searchKeyUpText = searchKeyUpText;
    }

    public void getAlbumTracks() {
        allTrack=spotifyEndpoint.getAlbum(albumId).tracks().items();
        LOGGER.info("jestem" + allTrack);
    }

    public void getAllAlbumsAfterSearch() {
        LOGGER.info("SpotifyServiceFacelets.getAllAlbumsAfterSearch()");
        SearchAlbumResponse album = spotifyEndpoint.getAlbumFromSearch(albumName);
        albums = album.albums().items();
        LOGGER.info("All albums after search: " + albums);

    }
//
    public void cos() {
        LOGGER.info("cos");
    }


    @Produces(MediaType.APPLICATION_JSON)
    public void getAlbumTracksAfterSearch() {
        LOGGER.info("SpotifyServiceFacelets.getAlbumTracksAfterSearch()");
        SearchAlbumResponse album = spotifyEndpoint.getAlbumFromSearch(albumName);
        LOGGER.info("Album found" + album);
        String albumId= album.albums().items().stream().findFirst().map(albumSpotify -> albumSpotify.id()).orElseThrow(()->new NotFoundException("Brak albumu"));
        LOGGER.info(" " + albumId);
//        allTrack = getAlbumTracks(albumId);
        LOGGER.info("List of added tracks " + allTrack);
    }

    public List<TrackSpotify> filterTracks() {
        List<TrackSpotify> trackForFilter = allTrack.stream().filter(trackSpotify -> trackSpotify.name().contains(searchKeyUpText)).collect(Collectors.toList());
        LOGGER.info("Filter allTrack " + allTrack);
        return trackForFilter;
    }

    public List<AlbumSpotify> filterAlbums() {
        List<AlbumSpotify> albumsForFilter = albums.stream().filter(albumSpotify -> albumSpotify.name().contains(searchAlbum)).collect(Collectors.toList());
        LOGGER.info("Filter allTrack " + allTrack);
        return albumsForFilter;
    }
}
