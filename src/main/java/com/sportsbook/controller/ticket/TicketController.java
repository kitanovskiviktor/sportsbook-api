package com.sportsbook.controller.ticket;

import com.sportsbook.dto.ticket.PlaceBetRequestDTO;
import com.sportsbook.dto.ticket.TicketResponseDTO;
import com.sportsbook.security.JwtUtil;
import com.sportsbook.service.ticket.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final JwtUtil jwtUtil;

    public TicketController(TicketService ticketService, JwtUtil jwtUtil) {
        this.ticketService = ticketService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<TicketResponseDTO> placeBet(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody PlaceBetRequestDTO request) {
        Long playerId = jwtUtil.extractPlayerId(authHeader.substring(7));
        return new ResponseEntity<>(ticketService.placeBet(playerId, request), HttpStatus.CREATED);
    }

    @GetMapping("/my")
    public List<TicketResponseDTO> myTickets(
            @RequestHeader("Authorization") String authHeader) {
        Long playerId = jwtUtil.extractPlayerId(authHeader.substring(7));
        return ticketService.getPlayerTickets(playerId);
    }
}
