package com.example.wildfly.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AlbumSongPK implements Serializable {
    @Column(name="ALBUM_ID")
    private Long albumId;

    @Column(name="SONG_ID")
    private Long songId;


    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getSongId() {
        return songId;
    }

    public void setSongId(Long songId) {
        this.songId = songId;
    }


}
