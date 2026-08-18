package com.sportsbook.service.sport;

import com.sportsbook.dto.sport.SportRequestDTO;
import com.sportsbook.dto.sport.SportResponseDTO;
import com.sportsbook.model.Sport.Sport;
import com.sportsbook.repository.sport.SportRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SportService {

    private final SportRepository sportRepository;

    public SportService(com.sportsbook.repository.sport.SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public List<SportResponseDTO> getAllSports() {
        return sportRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public SportResponseDTO getSportById(Long id) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sport not found with id: " + id));
        return toResponseDTO(sport);
    }

    public SportResponseDTO createSport(SportRequestDTO request) {
        Sport sport = new Sport();
        sport.setName(request.getName());
        Sport saved = sportRepository.save(sport);
        return toResponseDTO(saved);
    }

    public SportResponseDTO updateSport(Long id, SportRequestDTO request) {
        Sport sport = sportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sport not found with id: " + id));
        sport.setName(request.getName());
        Sport updated = sportRepository.save(sport);
        return toResponseDTO(updated);
    }

    public void deleteSport(Long id) {
        sportRepository.deleteById(id);
    }

    private SportResponseDTO toResponseDTO(Sport sport) {
        return new SportResponseDTO(sport.getId(), sport.getName());
    }
}
