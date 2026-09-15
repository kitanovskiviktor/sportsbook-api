package com.sportsbook.controller.user;

import com.sportsbook.dto.oddsTenant.OddsTenantRequest;
import com.sportsbook.model.tenant.OddsTenant.OddsTenant;
import com.sportsbook.security.AuthHelper;
import com.sportsbook.service.user.UserOperatorService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/operator")
public class UserOperatorController {

    private final UserOperatorService userOperatorService;
    private final AuthHelper authHelper;

    public UserOperatorController(UserOperatorService userOperatorService, AuthHelper authHelper) {
        this.userOperatorService = userOperatorService;
        this.authHelper = authHelper;
    }

    @GetMapping("/odds")
    public List<OddsTenant> getOddsOverrides(HttpServletRequest request) {
        authHelper.requireOperator(request);
        return userOperatorService.getAllOddsOverrides();
    }

    @PostMapping("/odds")
    public OddsTenant setOddsOverride(HttpServletRequest request,
                                    @RequestBody OddsTenantRequest body) {
        authHelper.requireOperator(request);
        return userOperatorService.setOddsOverride(body);
    }

    @DeleteMapping("/odds/{outcomeId}")
    public void deleteOverride(HttpServletRequest request, @PathVariable Long outcomeId) {
        authHelper.requireOperator(request);
        userOperatorService.deleteOddsOverride(outcomeId);
    }
}
