package app.vx.musicplayer.home.controller;

import app.vx.musicplayer.home.dto.GetHomePageResponse;
import app.vx.musicplayer.home.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController (HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping
    public ResponseEntity<GetHomePageResponse> getHome () {
        return ResponseEntity.ok(homeService.getHome());
    }

}
