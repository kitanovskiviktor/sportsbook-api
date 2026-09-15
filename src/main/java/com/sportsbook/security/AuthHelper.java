package com.sportsbook.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    private final JwtUtil jwtUtil;

    public AuthHelper(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    public String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            throw new RuntimeException("Missing token");
        }
        return header.substring(7);
    }

    public String getRole(HttpServletRequest request) {
        return jwtUtil.extractRole(getToken(request));
    }

    public String getTenantKey(HttpServletRequest request) {
        return jwtUtil.extractTenantKey(getToken(request));
    }

    public void requireSuperAdmin(HttpServletRequest request) {
        if (!"SUPER_ADMIN".equals(getRole(request))) {
            throw new RuntimeException("Access denied: SUPER_ADMIN required");
        }
    }
}