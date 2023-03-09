package com.example.wildfly.model.entity;


import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name="ALBUMS_SONGS")
public class AlbumSong implements Serializable {

    @EmbeddedId
    AlbumSongPK id;

    @Column(name="POSITION")
    private Integer position;

    @ManyToOne
    @JoinColumn(name="SONG_ID", referencedColumnName = "SONG_ID", insertable = false, updatable = false)
    private Song song;

    public AlbumSong() {
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public AlbumSongPK getId() {
        return id;
    }

    public void setId(AlbumSongPK id) {
        this.id = id;
    }


}
