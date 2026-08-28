package com.sportsbook.service.tree;

import com.sportsbook.dto.tree.*;
import com.sportsbook.model.Category.Category;
import com.sportsbook.model.Event.Event;
import com.sportsbook.model.League.League;
import com.sportsbook.model.Sport.Sport;
import com.sportsbook.repository.event.EventRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TreeService {

    private final EventRepository eventRepository;

    public TreeService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<SportTreeDTO> getTree(Integer hours) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = now.plusHours(hours != null ? hours : 24);

        List<Event> events = eventRepository.findByStartTimeBetween(now, end);

        Map<League, List<Event>> eventsByLeague = events.stream()
                .filter(e -> e.getLeague() != null)
                .collect(Collectors.groupingBy(Event::getLeague));

        Map<Category, List<League>> leaguesByCategory = eventsByLeague.keySet().stream()
                .filter(l -> l.getCategory() != null)
                .collect(Collectors.groupingBy(League::getCategory));

        Map<Sport, List<Category>> categoriesBySport = leaguesByCategory.keySet().stream()
                .filter(c -> c.getSport() != null)
                .collect(Collectors.groupingBy(Category::getSport));

        List<SportTreeDTO> tree = new ArrayList<>();

        for (Sport sport : categoriesBySport.keySet()) {
            SportTreeDTO sportDTO = new SportTreeDTO();
            sportDTO.setId(sport.getId());
            sportDTO.setName(sport.getName());
            sportDTO.setCategories(new ArrayList<>());

            for (Category category : categoriesBySport.get(sport)) {
                CategoryTreeDTO categoryDTO = new CategoryTreeDTO();
                categoryDTO.setId(category.getId());
                categoryDTO.setName(category.getName());
                categoryDTO.setLeagues(new ArrayList<>());

                for (League league : leaguesByCategory.get(category)) {
                    LeagueTreeDTO leagueDTO = new LeagueTreeDTO();
                    leagueDTO.setId(league.getId());
                    leagueDTO.setName(league.getName());

                    List<EventTreeDTO> eventDTOs = eventsByLeague.get(league).stream()
                            .map(this::toEventTreeDTO)
                            .collect(Collectors.toList());
                    leagueDTO.setEvents(eventDTOs);

                    categoryDTO.getLeagues().add(leagueDTO);
                }

                sportDTO.getCategories().add(categoryDTO);
            }

            tree.add(sportDTO);
        }

        return tree;
    }

    private EventTreeDTO toEventTreeDTO(Event event) {
        return new EventTreeDTO(
                event.getId(),
                event.getHomeTeam(),
                event.getAwayTeam(),
                event.getStartTime(),
                event.getStatus()
        );
    }
}