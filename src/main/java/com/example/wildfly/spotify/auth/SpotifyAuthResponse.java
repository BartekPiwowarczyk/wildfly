package com.example.wildfly.spotify.auth;


import com.fasterxml.jackson.annotation.JsonProperty;

public record SpotifyAuthResponse(
        String access_token,
        String token_type,
        Long expires_in)
 {
}
