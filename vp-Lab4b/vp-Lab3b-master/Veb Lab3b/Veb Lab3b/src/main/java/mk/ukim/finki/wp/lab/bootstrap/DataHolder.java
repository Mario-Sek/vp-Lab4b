package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.model.User;
import mk.ukim.finki.wp.lab.model.enumerations.Role;
import mk.ukim.finki.wp.lab.repository.jpa.AlbumRepositoryJpa;
import mk.ukim.finki.wp.lab.repository.jpa.ArtistRepositoryJpa;
import mk.ukim.finki.wp.lab.repository.jpa.SongRepositoryJpa;
import mk.ukim.finki.wp.lab.repository.jpa.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {

    public static List<Artist> artists = null;
    public static List<Song> songs = null;

    public static List<Album> albums = null;
    public static List<User> users = null;

    private final PasswordEncoder passwordEncoder;

    private final ArtistRepositoryJpa artistRepositoryJpa;
    private final SongRepositoryJpa songRepositoryJpa;
    private final AlbumRepositoryJpa albumRepositoryJpa;

    private final UserRepository userRepository;


    public DataHolder(PasswordEncoder passwordEncoder, ArtistRepositoryJpa artistRepositoryJpa, SongRepositoryJpa songRepositoryJpa, AlbumRepositoryJpa albumRepositoryJpa, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.artistRepositoryJpa = artistRepositoryJpa;
        this.songRepositoryJpa = songRepositoryJpa;
        this.albumRepositoryJpa = albumRepositoryJpa;
        this.userRepository = userRepository;
    }


    @PostConstruct
    public void init(){
        artists = new ArrayList<>();
        artists.add(new Artist(1L, "artist1", "ar1","aaaaa"));
        artists.add(new Artist(2L, "artist2", "ar2","bbbbb"));
        artists.add(new Artist(3L, "artist3", "ar3","ccccc"));
        artists.add(new Artist(4L, "artist4", "ar4","ddddd"));
        artists.add(new Artist(5L, "artist5", "ar5","eeeee"));
       this.artistRepositoryJpa.saveAll(artists);

        albums = new ArrayList<>();
        albums.add(new Album("album 1", "pop", "2001"));
        albums.add(new Album("album 2", "rock", "2002"));
        albums.add(new Album("album 3", "metal", "2003"));
        albums.add(new Album("album 4", "jazz", "2004"));
        albums.add(new Album("album 5", "edm", "2005"));
        this.albumRepositoryJpa.saveAll(albums);


        songs = new ArrayList<>();
        songs.add(new Song( "song1", "pop", 2001, albums.get(0)));
        songs.add(new Song( "song2", "rock", 2002, albums.get(1)));
        songs.add(new Song( "song3", "metal", 2003, albums.get(2)));
        songs.add(new Song("song4", "jazz", 2004, albums.get(3)));
        songs.add(new Song("song5", "edm", 2005, albums.get(4)));
        this.songRepositoryJpa.saveAll(songs);

         users = new ArrayList<>();
        if (this.userRepository.count() == 0) {
            users.add(new User("user", passwordEncoder.encode("user"), "user", "user", Role.ROLE_USER));
            users.add(new User("admin", passwordEncoder.encode("admin"), "admin", "admin", Role.ROLE_ADMIN));
            users.add(new User("moderator", passwordEncoder.encode("moderator"), "moderator", "moderator", Role.ROLE_MODERATOR));

            this.userRepository.saveAll(users);
        }


    }
}
