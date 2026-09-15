package com.sportsbook.service.user;

import com.sportsbook.dto.oddsTenant.OddsTenantRequest;
import com.sportsbook.model.tenant.OddsTenant.OddsTenant;
import com.sportsbook.repository.tenant.oddsTenant.OddsTenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOperatorService {

    private final OddsTenantRepository oddsTenantRepository;

    public UserOperatorService(OddsTenantRepository oddsTenantRepository) {
        this.oddsTenantRepository = oddsTenantRepository;
    }

    public List<OddsTenant> getAllOddsOverrides() {
        return oddsTenantRepository.findAll();
    }

    public OddsTenant setOddsOverride(OddsTenantRequest request) {
        OddsTenant override = oddsTenantRepository.findByOutcomeId(request.getOutcomeId())
                .orElse(new OddsTenant());
        override.setOutcomeId(request.getOutcomeId());
        override.setOverriddenOdds(request.getOverriddenOdds());
        return oddsTenantRepository.save(override);
    }

    public void deleteOddsOverride(Long outcomeId) {
        oddsTenantRepository.findByOutcomeId(outcomeId)
                .ifPresent(oddsTenantRepository::delete);
    }
}
