package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepositoryJpa;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepositoryJpa;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SongServiceImpl implements SongService {
    private final SongRepositoryJpa songRepository;
    private final ArtistRepositoryJpa artistRepository;

    public SongServiceImpl(SongRepositoryJpa songRepository, ArtistRepositoryJpa artistRepository) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    @Override
    public Artist addArtistToSong(Artist artist, Song song) {
        if (!song.getPerformers().contains(artist)) {
            song.getPerformers().add(artist);
            songRepository.save(song);
        }
        return artist;
    }

    @Override
    public Optional<Song> findById(Long trackId) {
        return songRepository.findById(trackId);
    }

    @Override
    public Optional<Song> save(String title, String genre, Integer releaseYear, Album album, Optional<Long>songId) {

        if (songId.isPresent()) {
            Optional<Song> song = songRepository.findById(songId.get());
            if (!song.isEmpty()) {
                song.get().setTitle(title);
                song.get().setGenre(genre);
                song.get().setReleaseYear(releaseYear);
                song.get().setAlbum(album);
                return Optional.of(songRepository.save(song.get()));
            } else {
                return Optional.of(songRepository.save(new Song(title, genre, releaseYear, album)));
            }
        } else {
            return Optional.of(songRepository.save(new Song(title, genre, releaseYear, album)));
        }
    }

    @Override
    public void deleteById(Long id) {
        this.songRepository.deleteById(id);
    }

    @Override
    public List<Song> findByAlbumId(Long albumId) {
        return songRepository.findByAlbum_Id(albumId);
    }


}
