package com.example.wildfly.spotify.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record SearchAlbumResponse(AlbumsSearched albums) {
    @JsonIgnoreProperties(ignoreUnknown = true)
    public record AlbumsSearched(List<AlbumSpotify> items ) {
    }
}
