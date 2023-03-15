package com.example.wildfly.model.dto;


import java.util.Collection;



public record AlbumDTO (String title, String edition, ArtistDTO artistDTO, Collection<AlbumSongsDTO> albumSongsDTO) {

}
