package com.sportsbook.controller.sport;

import com.sportsbook.dto.sport.SportRequestDTO;
import com.sportsbook.dto.sport.SportResponseDTO;
import com.sportsbook.service.sport.SportService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sports")
public class SportController {

    private final SportService sportService;

    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @GetMapping
    public List<SportResponseDTO> getAllSports() {
        return sportService.getAllSports();
    }

    @GetMapping("/{id}")
    public SportResponseDTO getSportById(@PathVariable Long id) {
        return sportService.getSportById(id);
    }

    @PostMapping
    public ResponseEntity<SportResponseDTO> createSport(@RequestBody SportRequestDTO request) {
        return new ResponseEntity<>(sportService.createSport(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public SportResponseDTO updateSport(@PathVariable Long id, @RequestBody SportRequestDTO request) {
        return sportService.updateSport(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSport(@PathVariable Long id) {
        sportService.deleteSport(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
