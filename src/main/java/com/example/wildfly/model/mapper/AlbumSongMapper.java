package com.example.wildfly.model.mapper;

import com.example.wildfly.model.dto.AlbumSongsDTO;
import com.example.wildfly.model.entity.AlbumSong;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(uses = SongMapper.class,componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface AlbumSongMapper {

    @Mapping(source="song",target = "song")
    AlbumSong fromAlbumSongDTO(AlbumSongsDTO albumSongsDTO);

    @Mapping(source="song",target = "song")
    AlbumSongsDTO fromAlbumSong(AlbumSong albumSong);
}
