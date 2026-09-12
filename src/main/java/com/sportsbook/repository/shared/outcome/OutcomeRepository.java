package com.sportsbook.repository.shared.outcome;

import com.sportsbook.model.shared.Outcome.Outcome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeRepository extends JpaRepository<Outcome, Long> {
}
