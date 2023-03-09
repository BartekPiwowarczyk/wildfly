package com.example.wildfly.spotify.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AlbumSpotify(@JsonProperty("name") String title, String id, List<ArtistSpotify> artists, TracksSpotify tracks) {

    public AlbumSpotify(String name, String id, List<ArtistSpotify> artists) {
        this(name, id, artists, null);
    }

    public record TracksSpotify(List<TrackSpotify> items) {
    }

}