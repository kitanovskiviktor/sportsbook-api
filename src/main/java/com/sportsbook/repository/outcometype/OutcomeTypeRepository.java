package com.sportsbook.repository.outcometype;

import com.sportsbook.model.OutcomeType.OutcomeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeTypeRepository extends JpaRepository<OutcomeType, Long> {

    List<OutcomeType> findByMarketTypeId(Long marketTypeId);
}
