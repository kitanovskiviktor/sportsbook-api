package com.sportsbook.service.markettype;

import com.sportsbook.dto.markettype.MarketTypeRequestDTO;
import com.sportsbook.dto.markettype.MarketTypeResponseDTO;
import com.sportsbook.dto.outcometype.OutcomeTypeResponseDTO;
import com.sportsbook.model.MarketType.MarketType;
import com.sportsbook.model.OutcomeType.OutcomeType;
import com.sportsbook.model.Sport.Sport;
import com.sportsbook.repository.markettype.MarketTypeRepository;
import com.sportsbook.repository.sport.SportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MarketTypeService {

    private final MarketTypeRepository marketTypeRepository;
    private final SportRepository sportRepository;

    public MarketTypeService(MarketTypeRepository marketTypeRepository, SportRepository sportRepository) {
        this.marketTypeRepository = marketTypeRepository;
        this.sportRepository = sportRepository;
    }

    public List<MarketTypeResponseDTO> getAllMarketTypes() {
        return marketTypeRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MarketTypeResponseDTO getMarketTypeById(Long id) {
        MarketType marketType = marketTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("MarketType not found with id: " + id));
        return toResponseDTO(marketType);
    }

    public List<MarketTypeResponseDTO> getMarketTypesBySport(Long sportId) {
        return marketTypeRepository.findBySportId(sportId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MarketTypeResponseDTO createMarketType(MarketTypeRequestDTO request) {
        Sport sport = sportRepository.findById(request.getSportId())
                .orElseThrow(() -> new RuntimeException("Sport not found with id: " + request.getSportId()));

        MarketType marketType = new MarketType();
        marketType.setName(request.getName());
        marketType.setSport(sport);

        if (request.getOutcomeTypes() != null) {
            for (String outcomeName : request.getOutcomeTypes()) {
                OutcomeType outcomeType = new OutcomeType();
                outcomeType.setName(outcomeName);
                outcomeType.setMarketType(marketType);
                marketType.getOutcomeTypes().add(outcomeType);
            }
        }

        MarketType saved = marketTypeRepository.save(marketType);
        return toResponseDTO(saved);
    }

    public void deleteMarketType(Long id) {
        marketTypeRepository.deleteById(id);
    }

    private MarketTypeResponseDTO toResponseDTO(MarketType marketType) {
        List<OutcomeTypeResponseDTO> outcomeDTOs = marketType.getOutcomeTypes()
                .stream()
                .map(ot -> new OutcomeTypeResponseDTO(ot.getId(), ot.getName()))
                .collect(Collectors.toList());

        return new MarketTypeResponseDTO(
                marketType.getId(),
                marketType.getName(),
                marketType.getSport() != null ? marketType.getSport().getId() : null,
                marketType.getSport() != null ? marketType.getSport().getName() : null,
                outcomeDTOs
        );
    }
}