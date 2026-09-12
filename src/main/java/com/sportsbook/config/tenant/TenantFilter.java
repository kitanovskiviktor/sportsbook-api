package com.sportsbook.config.tenant;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Order(1)
public class TenantFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String tenant = resolveTenant(request);
            TenantContext.setTenant(tenant);
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private String resolveTenant(HttpServletRequest request) {
        String headerTenant = request.getHeader("X-Tenant-ID");
        if (headerTenant != null && !headerTenant.isBlank()) {
            return headerTenant;
        }

        String host = request.getServerName();
        if (host.contains("branda")) return "brand_a";
        if (host.contains("brandb")) return "brand_b";

        return "brand_a";
    }
}