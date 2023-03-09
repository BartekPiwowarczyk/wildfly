package com.example.wildfly.spotify.model.dto;

import java.util.List;

public record SearchAlbumResponse(AlbumsSearched albums) {

    public record AlbumsSearched(List<AlbumSpotify> items ) {
    }
}
