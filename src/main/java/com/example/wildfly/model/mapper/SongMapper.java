package com.example.wildfly.model.mapper;


import com.example.wildfly.model.dto.SongDTO;
import com.example.wildfly.model.entity.Song;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface SongMapper {

    Song fromSongDTO(SongDTO songDTO);

    SongDTO fromSong(Song song);
}
