package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;

import java.util.List;
import java.util.Optional;

public interface SongService {
    List<Song> listSongs();
    Artist addArtistToSong(Artist artist, Song song);
    public Optional<Song> findById (Long trackId);

    Optional<Song> save(String title, String genre, Integer releaseYear, Album album, Optional<Long> songId);

    void deleteById(Long id);

    List<Song> findByAlbumId(Long albumId);
}
