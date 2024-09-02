package com.backend.ecommerce.presentation;


import com.backend.ecommerce.application.AuthServiceImpl;
import com.backend.ecommerce.application.UserDetailsServiceImpl;
import com.backend.ecommerce.application.dto.user.AuthResultDto;
import com.backend.ecommerce.application.dto.user.LoginDto;
import com.backend.ecommerce.application.dto.user.RegisterDto;
import com.backend.ecommerce.domain.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final AuthServiceImpl authService;

    public UserController(UserDetailsServiceImpl userDetailsServiceImpl, AuthServiceImpl authService) {
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.authService = authService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        var users = userDetailsServiceImpl.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") UUID id){
        var user = userDetailsServiceImpl.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResultDto> register(@RequestBody RegisterDto registerDto){
        return ResponseEntity.ok(authService.register(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResultDto> login(@RequestBody LoginDto loginDto){
        try{
            return ResponseEntity.ok(authService.authenticate(loginDto));
        }
        catch (AuthenticationException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
