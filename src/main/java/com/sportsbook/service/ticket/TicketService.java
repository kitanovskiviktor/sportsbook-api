package com.sportsbook.service.ticket;

import com.sportsbook.dto.ticket.*;
import com.sportsbook.exception.OddsChangedException;
import com.sportsbook.model.Event.Event;
import com.sportsbook.model.Market.Market;
import com.sportsbook.model.Outcome.Outcome;
import com.sportsbook.model.Player.Player;
import com.sportsbook.model.TicketSelection.TicketSelection;
import com.sportsbook.model.Tiket.Ticket;
import com.sportsbook.model.Tiket.TicketStatus;
import com.sportsbook.repository.outcome.OutcomeRepository;
import com.sportsbook.repository.player.PlayerRepository;
import com.sportsbook.repository.ticket.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final PlayerRepository playerRepository;
    private final OutcomeRepository outcomeRepository;

    public TicketService(TicketRepository ticketRepository,
                         PlayerRepository playerRepository,
                         OutcomeRepository outcomeRepository) {
        this.ticketRepository = ticketRepository;
        this.playerRepository = playerRepository;
        this.outcomeRepository = outcomeRepository;
    }

    @Transactional
    public TicketResponseDTO placeBet(Long playerId, PlaceBetRequestDTO request) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new RuntimeException("Player not found"));

        if (request.getStake() == null || request.getStake().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Stake must be positive");
        }

        if (request.getSelections() == null || request.getSelections().isEmpty()) {
            throw new RuntimeException("At least one selection required");
        }

        Ticket ticket = new Ticket();
        ticket.setPlayer(player);
        ticket.setStake(request.getStake());
        ticket.setStatus(TicketStatus.PENDING);
        ticket.setPlacedAt(LocalDateTime.now());

        BigDecimal totalOdds = BigDecimal.ONE;

        for (PlaceBetSelectionDTO sel : request.getSelections()) {
            Outcome outcome = outcomeRepository.findById(sel.getOutcomeId())
                    .orElseThrow(() -> new RuntimeException("Outcome not found: " + sel.getOutcomeId()));

            if (outcome.getOdds().compareTo(sel.getOdds()) != 0) {
                throw new OddsChangedException(
                        "Odds changed for outcome " + sel.getOutcomeId() +
                                ". Expected " + sel.getOdds() + " but current is " + outcome.getOdds());
            }

            Market market = outcome.getMarket();
            Event event = market.getEvent();

            TicketSelection ts = new TicketSelection();
            ts.setTicket(ticket);
            ts.setOutcomeId(outcome.getId());
            ts.setEventId(event.getId());
            ts.setHomeTeam(event.getHomeTeam());
            ts.setAwayTeam(event.getAwayTeam());
            ts.setMarketName(market.getMarketType().getName());
            ts.setOutcomeName(outcome.getOutcomeType().getName());
            ts.setEventStatus(event.getStatus());
            ts.setOddsAtPlacement(outcome.getOdds());

            ticket.getSelections().add(ts);
            totalOdds = totalOdds.multiply(outcome.getOdds());
        }

        ticket.setTotalOdds(totalOdds);
        ticket.setPotentialPayout(request.getStake().multiply(totalOdds));

        if (player.getBalance().compareTo(request.getStake()) < 0) {
            throw new RuntimeException("Insufficient balance");
        }

        player.setBalance(player.getBalance().subtract(request.getStake()));
        playerRepository.save(player);

        Ticket saved = ticketRepository.save(ticket);
        return toResponseDTO(saved);
    }

    public List<TicketResponseDTO> getPlayerTickets(Long playerId) {
        return ticketRepository.findByPlayerIdOrderByPlacedAtDesc(playerId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    private TicketResponseDTO toResponseDTO(Ticket ticket) {
        List<TicketSelectionResponseDTO> selections = ticket.getSelections().stream()
                .map(s -> new TicketSelectionResponseDTO(
                        s.getOutcomeId(), s.getEventId(), s.getHomeTeam(), s.getAwayTeam(),
                        s.getMarketName(), s.getOutcomeName(), s.getEventStatus(),
                        s.getOddsAtPlacement()))
                .collect(Collectors.toList());

        return new TicketResponseDTO(
                ticket.getId(), ticket.getStake(), ticket.getTotalOdds(),
                ticket.getPotentialPayout(), ticket.getStatus(),
                ticket.getPlacedAt(), selections);
    }
}
