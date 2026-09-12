package com.sportsbook.config.tenant;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class TenantDatabaseInitializer {

    private final EntityManagerFactory tenantEntityManagerFactory;

    public TenantDatabaseInitializer(
            @Qualifier("tenantEntityManagerFactory") EntityManagerFactory emf) {
        this.tenantEntityManagerFactory = emf;
    }

    @PostConstruct
    public void initializeAllTenants() {
        String[] tenants = { "brand_a", "brand_b" };

        for (String tenant : tenants) {
            TenantContext.setTenant(tenant);
            try {
                tenantEntityManagerFactory.createEntityManager().close();
            } catch (Exception e) {
                System.err.println("Failed to init tenant " + tenant + ": " + e.getMessage());
            } finally {
                TenantContext.clear();
            }
        }
    }
}
