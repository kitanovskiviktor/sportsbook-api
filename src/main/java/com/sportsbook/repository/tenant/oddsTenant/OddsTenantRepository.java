package com.sportsbook.repository.tenant.oddsTenant;

import com.sportsbook.model.tenant.OddsTenant.OddsTenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OddsTenantRepository extends JpaRepository<OddsTenant, Long> {
    Optional<OddsTenant> findByOutcomeId(Long outcomeId);
    List<OddsTenant> findByOutcomeIdIn(List<Long> outcomeIds);
}
