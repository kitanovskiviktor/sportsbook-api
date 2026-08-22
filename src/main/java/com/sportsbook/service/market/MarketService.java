package com.sportsbook.service.market;

import com.sportsbook.dto.market.*;
import com.sportsbook.dto.outcome.OutcomeResponseDTO;
import com.sportsbook.model.Event.Event;
import com.sportsbook.model.Market.Market;
import com.sportsbook.model.MarketType.MarketType;
import com.sportsbook.model.Outcome.Outcome;
import com.sportsbook.model.OutcomeType.OutcomeType;
import com.sportsbook.repository.event.EventRepository;
import com.sportsbook.repository.market.MarketRepository;
import com.sportsbook.repository.markettype.MarketTypeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MarketService {

    private final MarketRepository marketRepository;
    private final EventRepository eventRepository;
    private final MarketTypeRepository marketTypeRepository;

    public MarketService(MarketRepository marketRepository,
                         EventRepository eventRepository,
                         MarketTypeRepository marketTypeRepository) {
        this.marketRepository = marketRepository;
        this.eventRepository = eventRepository;
        this.marketTypeRepository = marketTypeRepository;
    }

    public MarketResponseDTO addMarketToEvent(MarketRequestDTO request) {
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + request.getEventId()));

        MarketType marketType = marketTypeRepository.findById(request.getMarketTypeId())
                .orElseThrow(() -> new RuntimeException("MarketType not found with id: " + request.getMarketTypeId()));

        Market market = new Market();
        market.setEvent(event);
        market.setMarketType(marketType);

        Map<Long, BigDecimal> oddsByOutcomeType = request.getOdds().stream()
                .collect(Collectors.toMap(OddInputDTO::getOutcomeTypeId, OddInputDTO::getOdds));

        for (OutcomeType outcomeType : marketType.getOutcomeTypes()) {
            BigDecimal odds = oddsByOutcomeType.get(outcomeType.getId());
            if (odds == null) {
                throw new RuntimeException("Missing odds for outcomeType: " + outcomeType.getName());
            }

            Outcome outcome = new Outcome();
            outcome.setMarket(market);
            outcome.setOutcomeType(outcomeType);
            outcome.setOdds(odds);
            market.getOutcomes().add(outcome);
        }

        Market saved = marketRepository.save(market);
        return toResponseDTO(saved);
    }

    public List<MarketResponseDTO> getMarketsByEvent(Long eventId) {
        return marketRepository.findByEventId(eventId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public MarketResponseDTO getMarketById(Long id) {
        Market market = marketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Market not found with id: " + id));
        return toResponseDTO(market);
    }

    public void deleteMarket(Long id) {
        marketRepository.deleteById(id);
    }

    private MarketResponseDTO toResponseDTO(Market market) {
        List<OutcomeResponseDTO> outcomeDTOs = market.getOutcomes().stream()
                .map(o -> new OutcomeResponseDTO(
                        o.getId(),
                        o.getOutcomeType() != null ? o.getOutcomeType().getId() : null,
                        o.getOutcomeType() != null ? o.getOutcomeType().getName() : null,
                        o.getOdds()
                ))
                .collect(Collectors.toList());

        return new MarketResponseDTO(
                market.getId(),
                market.getEvent() != null ? market.getEvent().getId() : null,
                market.getMarketType() != null ? market.getMarketType().getId() : null,
                market.getMarketType() != null ? market.getMarketType().getName() : null,
                outcomeDTOs
        );
    }
}
