package com.sportsbook.controller.user;

import com.sportsbook.dto.user.*;
import com.sportsbook.security.AuthHelper;
import com.sportsbook.service.user.UserBrandService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/brands")
public class UserBrandController {

    private final UserBrandService userBrandService;
    private final AuthHelper authHelper;

    public UserBrandController(UserBrandService userBrandService, AuthHelper authHelper) {
        this.userBrandService = userBrandService;
        this.authHelper = authHelper;
    }

    @GetMapping
    public List<BrandResponse> getAllBrands(HttpServletRequest request) {
        authHelper.requireSuperAdmin(request);
        return userBrandService.getAllBrands();
    }

    @PutMapping("/{tenantKey}")
    public BrandResponse updateBrand(HttpServletRequest request,
                                     @PathVariable String tenantKey,
                                     @RequestBody BrandUpdateRequest body) {
        authHelper.requireSuperAdmin(request);
        return userBrandService.updateBrand(tenantKey, body);
    }

    @GetMapping("/{tenantKey}/operators")
    public List<OperatorResponse> getOperators(HttpServletRequest request,
                                               @PathVariable String tenantKey) {
        authHelper.requireSuperAdmin(request);
        return userBrandService.getOperatorsByBrand(tenantKey);
    }

    @PostMapping("/operators")
    public OperatorResponse createOperator(HttpServletRequest request,
                                           @RequestBody CreateOperatorRequest body) {
        authHelper.requireSuperAdmin(request);
        return userBrandService.createOperator(body);
    }
}
