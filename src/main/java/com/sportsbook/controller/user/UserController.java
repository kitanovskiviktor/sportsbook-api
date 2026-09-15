package com.sportsbook.controller.user;

import com.sportsbook.dto.user.UserAuthResponse;
import com.sportsbook.dto.user.UserLoginRequest;
import com.sportsbook.service.user.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public UserAuthResponse login(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }
}
