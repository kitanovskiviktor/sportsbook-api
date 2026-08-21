package com.sportsbook.repository.league;

import com.sportsbook.model.League.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

    List<League> findByCategoryId(Long categoryId);

    List<League> findBySportId(Long sportId);
}
