package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ArtistController {

    private final ArtistService artistService;
    private final SongService songService;

    public ArtistController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping("/artist")
    public String getArtistList(Model model) {
        List<Artist> listedArtists = artistService.listArtists();
        model.addAttribute("listedArtists", listedArtists);
        model.addAttribute("bodyContent", "artistList");
        return "master-template";
    }

    @PostMapping("/artist")
    public String postArtistList(@RequestParam(value = "trackId", required = false, defaultValue = "no trackId") String trackId,
                                 Model model) {
        List<Artist> listedArtists = artistService.listArtists();
        model.addAttribute("trackId", trackId);
        model.addAttribute("listedArtists", listedArtists);
        model.addAttribute("bodyContent", "artistList");
        return "master-template";
    }
}
