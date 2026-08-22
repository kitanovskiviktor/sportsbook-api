package com.sportsbook.repository.market;

import com.sportsbook.model.Market.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    List<Market> findByEventId(Long eventId);
}
