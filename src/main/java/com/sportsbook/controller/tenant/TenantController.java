package com.sportsbook.controller.tenant;

import com.sportsbook.dto.tenant.TenantConfigDTO;
import com.sportsbook.service.tenant.TenantService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tenant")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/config")
    public TenantConfigDTO getConfig(HttpServletRequest request) {
        String domain = request.getServerName();
        return tenantService.getConfigByDomain(domain);
    }
}
