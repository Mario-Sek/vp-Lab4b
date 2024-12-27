package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.model.exceptions.InvalidAlbumIdException;
import mk.ukim.finki.wp.lab.service.AlbumService;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class SongController {

    private final SongService songService;
    private final AlbumService albumService;
    private final ArtistService artistService;

    public SongController(SongService songService, AlbumService albumService, ArtistService artistService) {
        this.songService = songService;
        this.albumService = albumService;
        this.artistService = artistService;
    }

    @GetMapping("/songs")
    public String getSongsPage(@RequestParam(required = false) String error, @RequestParam(required = false) Long albumId ,Model model){

        if (error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        List<Song> songs = songService.listSongs();
        List<Album> albums = albumService.findAll();

        if (albumId != null && albumId != -1) {
            songs = songService.findByAlbumId(albumId);
        }

        model.addAttribute("albums", albums);
        model.addAttribute("songs", songs);
        model.addAttribute("bodyContent", "listSongs");
        return "master-template";
    }



    @GetMapping("/songs/edit-form/{songId}")
    public String editSong(@PathVariable Long songId, Model model) {
        if (songService.findById(songId).isPresent()) {
            Song song = songService.findById(songId).get();
            List<Album> albums = albumService.findAll();
            model.addAttribute("albums", albums);
            model.addAttribute("song", song);
            model.addAttribute("bodyContent", "add-song");
            return "master-template";
        }
        return "redirect:/songs?error=Song not found";
    }


    @GetMapping("/songs/add-form")
    public String getAddSongPage(Model model){
        List<Artist> artists = artistService.listArtists();
        List<Album> albums = albumService.findAll();
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        model.addAttribute("bodyContent", "add-song");
        return "master-template";
    }

    @PostMapping("/songs/add")
    public String saveSong(@RequestParam(required = false) String title,
                           @RequestParam Optional<Long> trackId,
                           @RequestParam(required = false) String genre,
                           @RequestParam(required = false) Integer releaseYear,
                           @RequestParam(required = false) Long albumId){

        Album album = albumService.findById(albumId).orElseThrow(() -> new InvalidAlbumIdException(albumId));

        if(trackId == null){
            this.songService.save(title, genre, releaseYear, album,trackId);
            return "redirect:/songs";
        }

        Album albumTmp = albumService.findById(albumId).orElse(null);

        songService.save(title, genre, releaseYear, albumTmp,trackId);

        return "redirect:/songs";
    }



    @GetMapping("/songs/delete/{id}")
    public String deleteSong(@PathVariable Long id){
        this.songService.deleteById(id);
        return "redirect:/songs";
    }

    @GetMapping("/filter")
    public String filterSongs(@RequestParam Long albumId, Model model) {
        if(albumId == -1){
            return "redirect:/songs";
        }
        List<Song> songs = songService.findByAlbumId(albumId);
        List<Album> albums = albumService.findAll();
        Optional<Album> selectedAlbum = albumService.findById(albumId);
        model.addAttribute("songs", songs);
        model.addAttribute("albums", albums);
        model.addAttribute("selectedAlbum", selectedAlbum.get());
        model.addAttribute("bodyContent", "listSongs");
        return "master-template";
    }



}
