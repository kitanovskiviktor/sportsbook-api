package com.sportsbook.repository.event;

import com.sportsbook.model.Event.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByLeagueId(Long leagueId);

    List<Event> findByStatus(String status);

    List<Event> findByStartTimeBetween(LocalDateTime start, java.time.LocalDateTime end);
}
