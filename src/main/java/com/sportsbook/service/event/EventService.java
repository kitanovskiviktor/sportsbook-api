package com.sportsbook.service.event;

import com.sportsbook.dto.event.EventRequestDTO;
import com.sportsbook.dto.event.EventResponseDTO;
import com.sportsbook.model.Event.Event;
import com.sportsbook.model.League.League;
import com.sportsbook.repository.event.EventRepository;
import com.sportsbook.repository.league.LeagueRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final EventRepository eventRepository;
    private final LeagueRepository leagueRepository;

    public EventService(EventRepository eventRepository, LeagueRepository leagueRepository) {
        this.eventRepository = eventRepository;
        this.leagueRepository = leagueRepository;
    }

    public List<EventResponseDTO> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EventResponseDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        return toResponseDTO(event);
    }

    public List<EventResponseDTO> getEventsByLeague(Long leagueId) {
        return eventRepository.findByLeagueId(leagueId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<EventResponseDTO> getEventsByStatus(String status) {
        return eventRepository.findByStatus(status)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public EventResponseDTO createEvent(EventRequestDTO request) {
        League league = leagueRepository.findById(request.getLeagueId())
                .orElseThrow(() -> new RuntimeException("League not found with id: " + request.getLeagueId()));

        Event event = new Event();
        event.setHomeTeam(request.getHomeTeam());
        event.setAwayTeam(request.getAwayTeam());
        event.setStartTime(request.getStartTime());
        event.setStatus(request.getStatus());
        event.setLeague(league);

        Event saved = eventRepository.save(event);
        return toResponseDTO(saved);
    }

    public EventResponseDTO updateEvent(Long id, EventRequestDTO request) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));

        event.setHomeTeam(request.getHomeTeam());
        event.setAwayTeam(request.getAwayTeam());
        event.setStartTime(request.getStartTime());
        event.setStatus(request.getStatus());

        if (request.getLeagueId() != null) {
            League league = leagueRepository.findById(request.getLeagueId())
                    .orElseThrow(() -> new RuntimeException("League not found with id: " + request.getLeagueId()));
            event.setLeague(league);
        }

        Event updated = eventRepository.save(event);
        return toResponseDTO(updated);
    }

    public EventResponseDTO updateEventStatus(Long id, String status) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
        event.setStatus(status);
        Event updated = eventRepository.save(event);
        return toResponseDTO(updated);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    private EventResponseDTO toResponseDTO(Event event) {
        return new EventResponseDTO(
                event.getId(),
                event.getHomeTeam(),
                event.getAwayTeam(),
                event.getStartTime(),
                event.getStatus(),
                event.getLeague() != null ? event.getLeague().getId() : null,
                event.getLeague() != null ? event.getLeague().getName() : null
        );
    }
}