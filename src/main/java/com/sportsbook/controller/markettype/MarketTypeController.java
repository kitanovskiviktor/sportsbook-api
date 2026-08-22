package com.sportsbook.controller.markettype;

import com.sportsbook.dto.markettype.MarketTypeRequestDTO;
import com.sportsbook.dto.markettype.MarketTypeResponseDTO;
import com.sportsbook.service.markettype.MarketTypeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/market-types")
public class MarketTypeController {

    private final MarketTypeService marketTypeService;

    public MarketTypeController(MarketTypeService marketTypeService) {
        this.marketTypeService = marketTypeService;
    }

    @GetMapping
    public List<MarketTypeResponseDTO> getAllMarketTypes() {
        return marketTypeService.getAllMarketTypes();
    }

    @GetMapping("/{id}")
    public MarketTypeResponseDTO getMarketTypeById(@PathVariable Long id) {
        return marketTypeService.getMarketTypeById(id);
    }

    @GetMapping("/by-sport/{sportId}")
    public List<MarketTypeResponseDTO> getMarketTypesBySport(@PathVariable Long sportId) {
        return marketTypeService.getMarketTypesBySport(sportId);
    }

    @PostMapping
    public ResponseEntity<MarketTypeResponseDTO> createMarketType(@RequestBody MarketTypeRequestDTO request) {
        return new ResponseEntity<>(marketTypeService.createMarketType(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMarketType(@PathVariable Long id) {
        marketTypeService.deleteMarketType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
