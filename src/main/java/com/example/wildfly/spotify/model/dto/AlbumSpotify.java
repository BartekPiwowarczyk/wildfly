package com.example.wildfly.spotify.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AlbumSpotify(String name, String id,  List<ArtistSpotify> artists, TracksSpotify tracks) {

    public AlbumSpotify(String name, String id, List<ArtistSpotify> artists) {
        this(name, id, artists, null);
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record TracksSpotify(List<TrackSpotify> items) {
    }

}