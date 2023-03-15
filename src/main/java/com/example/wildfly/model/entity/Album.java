package com.example.wildfly.model.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.List;


@NamedQuery(name="Album.findById",query="select distinct al from Album al join fetch al.artistId art join fetch al.albumSongs so join fetch so.song where al.id= :id order by so.position asc")
@NamedQuery(name="Album.findByIdWithResultTransformer",query = "select a.id,a.title,a.edition, ar, aso from Album a  join a.artistId ar left join a.albumSongs aso left join fetch aso.song song where a.id=:id")
@NamedQuery(name = "Album.findWithResultTransformer",query = "select a.id,a.title,a.edition, ar, aso.position, song.title,song.remarks,song.duration from Album a left join a.artistId ar left join a.albumSongs aso left join aso.song song")
@Entity
@Table(name="ALBUMS")
public class Album implements Serializable {

    @Column(name="ALBUM_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="TITLE")
    private String title;

    @Column(name = "EDITION")
    private String edition;

    @ManyToOne
    @JoinColumn(name = "ARTIST_ID", referencedColumnName = "ARTIST_ID")
    private Artist artistId;

    @OneToMany
    @JoinColumn(name = "ALBUM_ID", referencedColumnName="ALBUM_ID")
    private List<AlbumSong> albumSongs;


    public Album() {
    }

    public Album(Long id, String title, String edition, Artist artistId, List<AlbumSong> albumSongs) {
        this.id = id;
        this.title = title;
        this.edition = edition;
        this.artistId = artistId;
        this.albumSongs = albumSongs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public Artist getArtistId() {
        return artistId;
    }

    public void setArtistId(Artist artistId) {
        this.artistId = artistId;
    }

    public List<AlbumSong> getAlbumSongs() {
        return albumSongs;
    }

    public void setAlbumSongs(List<AlbumSong> albumSongs) {
        this.albumSongs = albumSongs;
    }
}
