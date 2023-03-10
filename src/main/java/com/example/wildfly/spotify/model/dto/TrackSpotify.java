package com.example.wildfly.spotify.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TrackSpotify(String name, Integer duration_ms, Integer track_number) {
}
