package com.sportsbook.service.user;

import com.sportsbook.dto.user.BrandResponse;
import com.sportsbook.dto.user.BrandUpdateRequest;
import com.sportsbook.dto.user.CreateOperatorRequest;
import com.sportsbook.dto.user.OperatorResponse;
import com.sportsbook.model.shared.Tenant.Tenant;
import com.sportsbook.model.shared.User.Role;
import com.sportsbook.model.shared.User.User;
import com.sportsbook.repository.shared.tenant.TenantRepository;
import com.sportsbook.repository.shared.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserBrandService {

    private final TenantRepository tenantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserBrandService(TenantRepository tenantRepository,
                             UserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.tenantRepository = tenantRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<BrandResponse> getAllBrands() {
        return tenantRepository.findAll().stream()
                .map(this::toBrandResponse)
                .collect(Collectors.toList());
    }

    public BrandResponse updateBrand(String tenantKey, BrandUpdateRequest request) {
        Tenant tenant = tenantRepository.findByTenantKey(tenantKey)
                .orElseThrow(() -> new RuntimeException("Brand not found: " + tenantKey));

        if (request.getBrandName() != null) tenant.setBrandName(request.getBrandName());
        if (request.getPrimaryColor() != null) tenant.setPrimaryColor(request.getPrimaryColor());
        if (request.getLogoUrl() != null) tenant.setLogoUrl(request.getLogoUrl());

        return toBrandResponse(tenantRepository.save(tenant));
    }

    public List<OperatorResponse> getOperatorsByBrand(String tenantKey) {
        return userRepository.findAll().stream()
                .filter(u -> u.getRole() == Role.OPERATOR && tenantKey.equals(u.getTenantKey()))
                .map(u -> new OperatorResponse(u.getId(), u.getUsername(), u.getTenantKey()))
                .collect(Collectors.toList());
    }

    public OperatorResponse createOperator(CreateOperatorRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already taken");
        }

        tenantRepository.findByTenantKey(request.getTenantKey())
                .orElseThrow(() -> new RuntimeException("Brand not found: " + request.getTenantKey()));

        User operator = new User();
        operator.setUsername(request.getUsername());
        operator.setPassword(passwordEncoder.encode(request.getPassword()));
        operator.setRole(Role.OPERATOR);
        operator.setTenantKey(request.getTenantKey());

        User saved = userRepository.save(operator);
        return new OperatorResponse(saved.getId(), saved.getUsername(), saved.getTenantKey());
    }

    private BrandResponse toBrandResponse(Tenant t) {
        return new BrandResponse(t.getId(), t.getTenantKey(), t.getDomain(),
                t.getBrandName(), t.getPrimaryColor(), t.getLogoUrl());
    }
}
