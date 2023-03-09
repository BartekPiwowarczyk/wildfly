package com.example.wildfly.model.mapper;



//"java(new com.example.model.entity.Artist())"

import com.example.wildfly.model.dto.AlbumDTO;
import com.example.wildfly.model.entity.Album;
import com.example.wildfly.service.ArtistService;
import com.example.wildfly.service.SongService;
import jakarta.inject.Inject;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(uses={ArtistMapper.class,AlbumSongMapper.class},componentModel = MappingConstants.ComponentModel.JAKARTA)
public abstract class AlbumMapper {

    @Inject
    ArtistService artistService;

    @Inject
    SongService songService;

    @Mapping(target = "artistId",expression = "java(artistService.findOrCreateArtist(albumDTO.artistDTO()))")
    @Mapping(target = "albumSongs",expression = "java(new java.util.ArrayList<AlbumSong>())")
//    @Mapping(target = "albumSongs",expression = )
//    @Mapping(source="artistDTO",target = "artistId")
    public abstract Album fromAlbumDTO(AlbumDTO albumDTO);


    @Mapping(source = "artistId",target = "artistDTO")
//    @Mapping(target = "songs",expression = "java(getAlbumSongsDTO(album.getAlbumSongs()))")
    @Mapping(source = "albumSongs",target = "albumSongsDTO")
    public abstract AlbumDTO fromAlbum(Album album);


//    public List<AlbumSongsDTO> getAlbumSongsDTO(List<AlbumSong> albumSongs) {
//        return albumSongs.stream()
//                .map(as -> new AlbumSongsDTO(as.getPosition(),new SongDTO(as.getSong().getTitle(),as.getSong().getRemarks(),as.getSong().getDuration())))
//                .collect(Collectors.toList());
//    }


}
