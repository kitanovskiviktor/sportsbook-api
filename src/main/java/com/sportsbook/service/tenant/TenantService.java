package com.sportsbook.service.tenant;


import com.sportsbook.dto.tenant.TenantConfigDTO;
import com.sportsbook.repository.shared.tenant.TenantRepository;
import org.springframework.stereotype.Service;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    public TenantConfigDTO getConfigByDomain(String domain) {
        return tenantRepository.findByDomain(domain)
                .map(t -> new TenantConfigDTO(
                        t.getTenantKey(), t.getBrandName(),
                        t.getPrimaryColor(), t.getLogoUrl()))
                .orElseThrow(() -> new RuntimeException("Tenant not found for domain: " + domain));
    }
}
