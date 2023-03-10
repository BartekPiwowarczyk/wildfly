package com.example.wildfly.spotify.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ArtistSpotify(String id, String name) {
}
