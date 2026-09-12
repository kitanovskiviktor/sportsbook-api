package com.sportsbook.repository.shared.tenant;

import com.sportsbook.model.shared.Tenant.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
    Optional<Tenant> findByDomain(String domain);
    Optional<Tenant> findByTenantKey(String tenantKey);
}
