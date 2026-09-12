package com.sportsbook.repository.shared.outcometype;

import com.sportsbook.model.shared.OutcomeType.OutcomeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutcomeTypeRepository extends JpaRepository<OutcomeType, Long> {

    List<OutcomeType> findByMarketTypeId(Long marketTypeId);
}
