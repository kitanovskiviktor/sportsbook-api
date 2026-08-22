package com.sportsbook.repository.markettype;

import com.sportsbook.model.MarketType.MarketType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketTypeRepository extends JpaRepository<MarketType, Long> {

    List<MarketType> findBySportId(Long sportId);
}
