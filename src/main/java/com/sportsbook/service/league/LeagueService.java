package com.sportsbook.service.league;

import com.sportsbook.dto.league.LeagueRequestDTO;
import com.sportsbook.dto.league.LeagueResponseDTO;
import com.sportsbook.model.Category.Category;
import com.sportsbook.model.League.League;
import com.sportsbook.model.Sport.Sport;
import com.sportsbook.repository.category.CategoryRepository;
import com.sportsbook.repository.league.LeagueRepository;
import com.sportsbook.repository.sport.SportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final SportRepository sportRepository;
    private final CategoryRepository categoryRepository;

    public LeagueService(LeagueRepository leagueRepository,
                         SportRepository sportRepository,
                         CategoryRepository categoryRepository) {
        this.leagueRepository = leagueRepository;
        this.sportRepository = sportRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<LeagueResponseDTO> getAllLeagues() {
        return leagueRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public LeagueResponseDTO getLeagueById(Long id) {
        League league = leagueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("League not found with id: " + id));
        return toResponseDTO(league);
    }

    public List<LeagueResponseDTO> getLeaguesByCategory(Long categoryId) {
        return leagueRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<LeagueResponseDTO> getLeaguesBySport(Long sportId) {
        return leagueRepository.findBySportId(sportId)
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public LeagueResponseDTO createLeague(LeagueRequestDTO request) {
        Sport sport = sportRepository.findById(request.getSportId())
                .orElseThrow(() -> new RuntimeException("Sport not found with id: " + request.getSportId()));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));

        League league = new League();
        league.setName(request.getName());
        league.setSport(sport);
        league.setCategory(category);

        League saved = leagueRepository.save(league);
        return toResponseDTO(saved);
    }

    public LeagueResponseDTO updateLeague(Long id, LeagueRequestDTO request) {
        League league = leagueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("League not found with id: " + id));

        league.setName(request.getName());

        if (request.getSportId() != null) {
            Sport sport = sportRepository.findById(request.getSportId())
                    .orElseThrow(() -> new RuntimeException("Sport not found with id: " + request.getSportId()));
            league.setSport(sport);
        }
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found with id: " + request.getCategoryId()));
            league.setCategory(category);
        }

        League updated = leagueRepository.save(league);
        return toResponseDTO(updated);
    }

    public void deleteLeague(Long id) {
        leagueRepository.deleteById(id);
    }

    private LeagueResponseDTO toResponseDTO(League league) {
        return new LeagueResponseDTO(
                league.getId(),
                league.getName(),
                league.getSport() != null ? league.getSport().getId() : null,
                league.getSport() != null ? league.getSport().getName() : null,
                league.getCategory() != null ? league.getCategory().getId() : null,
                league.getCategory() != null ? league.getCategory().getName() : null
        );
    }
}
