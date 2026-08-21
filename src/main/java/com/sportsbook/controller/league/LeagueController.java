package com.sportsbook.controller.league;

import com.sportsbook.dto.league.LeagueRequestDTO;
import com.sportsbook.dto.league.LeagueResponseDTO;
import com.sportsbook.service.league.LeagueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leagues")
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public List<LeagueResponseDTO> getAllLeagues() {
        return leagueService.getAllLeagues();
    }

    @GetMapping("/{id}")
    public LeagueResponseDTO getLeagueById(@PathVariable Long id) {
        return leagueService.getLeagueById(id);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<LeagueResponseDTO> getLeaguesByCategory(@PathVariable Long categoryId) {
        return leagueService.getLeaguesByCategory(categoryId);
    }

    @GetMapping("/by-sport/{sportId}")
    public List<LeagueResponseDTO> getLeaguesBySport(@PathVariable Long sportId) {
        return leagueService.getLeaguesBySport(sportId);
    }

    @PostMapping
    public ResponseEntity<LeagueResponseDTO> createLeague(@RequestBody LeagueRequestDTO request) {
        return new ResponseEntity<>(leagueService.createLeague(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public LeagueResponseDTO updateLeague(@PathVariable Long id, @RequestBody LeagueRequestDTO request) {
        return leagueService.updateLeague(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeague(@PathVariable Long id) {
        leagueService.deleteLeague(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
