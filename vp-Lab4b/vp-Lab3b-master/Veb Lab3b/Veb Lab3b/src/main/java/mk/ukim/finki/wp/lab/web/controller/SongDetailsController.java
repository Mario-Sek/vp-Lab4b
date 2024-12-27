package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class SongDetailsController {

    private final SongService songService;
    private final ArtistService artistService;

    @Autowired
    public SongDetailsController(SongService songService, ArtistService artistService) {
        this.songService = songService;
        this.artistService = artistService;
    }


    @PostMapping("/songDetails")
    public String GetSongDetails(@RequestParam Long trackId,
                                 @RequestParam Long artistId,
                                 Model model) {
        Optional<Song> song = songService.findById(trackId);
        Optional<Artist> artist = artistService.findById(artistId);
        if (song.isPresent() && artist.isPresent()) {
            songService.addArtistToSong(artist.get(), song.get());
            model.addAttribute("song", song.get());
        }
        model.addAttribute("bodyContent", "songDetails");
        return "master-template";
    }
}
