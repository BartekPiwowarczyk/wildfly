package com.example.wildfly.model.mapper;


import com.example.wildfly.model.dto.ArtistDTO;
import com.example.wildfly.model.entity.Artist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA)
public interface ArtistMapper {

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "firstname",ignore = true)
    Artist fromArtistDTO(ArtistDTO artistDTO);

    ArtistDTO fromArtist(Artist artist);

}
