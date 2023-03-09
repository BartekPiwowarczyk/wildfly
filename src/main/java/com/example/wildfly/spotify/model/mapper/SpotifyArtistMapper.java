package com.example.wildfly.spotify.model.mapper;


import com.example.wildfly.model.entity.Artist;
import com.example.wildfly.spotify.model.dto.ArtistSpotify;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface SpotifyArtistMapper {

    @Mapping(target = "name",source = "name")
    @Mapping(target = "id",ignore = true)
    public Artist fromArtistSpotify(ArtistSpotify artistSpotify);
}
