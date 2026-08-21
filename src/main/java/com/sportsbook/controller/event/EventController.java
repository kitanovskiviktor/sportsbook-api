package com.sportsbook.controller.event;

import com.sportsbook.dto.event.EventRequestDTO;
import com.sportsbook.dto.event.EventResponseDTO;
import com.sportsbook.service.event.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<EventResponseDTO> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public EventResponseDTO getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    @GetMapping("/by-league/{leagueId}")
    public List<EventResponseDTO> getEventsByLeague(@PathVariable Long leagueId) {
        return eventService.getEventsByLeague(leagueId);
    }

    @GetMapping("/by-status/{status}")
    public List<EventResponseDTO> getEventsByStatus(@PathVariable String status) {
        return eventService.getEventsByStatus(status);
    }

    @PostMapping
    public ResponseEntity<EventResponseDTO> createEvent(@RequestBody EventRequestDTO request) {
        return new ResponseEntity<>(eventService.createEvent(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public EventResponseDTO updateEvent(@PathVariable Long id, @RequestBody EventRequestDTO request) {
        return eventService.updateEvent(id, request);
    }

    @PatchMapping("/{id}/status")
    public EventResponseDTO updateEventStatus(@PathVariable Long id, @RequestParam String status) {
        return eventService.updateEventStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
