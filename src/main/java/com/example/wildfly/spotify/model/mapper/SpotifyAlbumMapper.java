package com.example.wildfly.spotify.model.mapper;

import com.example.wildfly.model.entity.Album;
import com.example.wildfly.spotify.model.dto.AlbumSpotify;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(uses={SpotifyArtistMapper.class,SpotifySongMapper.class,SpotifyAlbumSongMapper.class},componentModel = MappingConstants.ComponentModel.JAKARTA)
public abstract class SpotifyAlbumMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "edition",source = "title")
    @Mapping(target = "artistId",ignore = true)
    @Mapping(target = "albumSongs",ignore = true)
    public abstract Album fromAlbumSpotifyResponse(AlbumSpotify albumSpotify);
}
