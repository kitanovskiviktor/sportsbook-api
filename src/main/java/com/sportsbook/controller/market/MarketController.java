package com.sportsbook.controller.market;

import com.sportsbook.dto.market.MarketRequestDTO;
import com.sportsbook.dto.market.MarketResponseDTO;
import com.sportsbook.service.market.MarketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/markets")
public class MarketController {

    private final MarketService marketService;

    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping("/{id}")
    public MarketResponseDTO getMarketById(@PathVariable Long id) {
        return marketService.getMarketById(id);
    }

    @GetMapping("/by-event/{eventId}")
    public List<MarketResponseDTO> getMarketsByEvent(@PathVariable Long eventId) {
        return marketService.getMarketsByEvent(eventId);
    }

    @PostMapping
    public ResponseEntity<MarketResponseDTO> addMarketToEvent(@RequestBody MarketRequestDTO request) {
        return new ResponseEntity<>(marketService.addMarketToEvent(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarket(@PathVariable Long id) {
        marketService.deleteMarket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
