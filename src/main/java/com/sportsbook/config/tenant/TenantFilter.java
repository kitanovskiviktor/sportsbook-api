package com.sportsbook.config.tenant;

import com.sportsbook.model.shared.Tenant.Tenant;
import com.sportsbook.repository.shared.tenant.TenantRepository;
import com.sportsbook.security.JwtUtil;
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

    private final TenantRepository tenantRepository;
    private final JwtUtil jwtUtil;

    public TenantFilter(TenantRepository tenantRepository, JwtUtil jwtUtil) {
        this.tenantRepository = tenantRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String tenant = resolveTenant(request);
            if (tenant != null) {
                TenantContext.setTenant(tenant);
            }
            filterChain.doFilter(request, response);
        } finally {
            TenantContext.clear();
        }
    }

    private String resolveTenant(HttpServletRequest request) {
        String header = request.getHeader("X-Tenant-ID");
        if (header != null && !header.isBlank()) {
            return header;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            try {
                String token = authHeader.substring(7);
                String tenantKey = jwtUtil.extractTenantKey(token);
                if (tenantKey != null && !tenantKey.isBlank()) {
                    return tenantKey;
                }
            } catch (Exception e) {
            }
        }

        String host = request.getServerName();
        return tenantRepository.findByDomain(host)
                .map(Tenant::getTenantKey)
                .orElse(null);
    }
}