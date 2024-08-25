package com.backend.ecommerce.application;

import com.backend.ecommerce.application.dto.user.LoginDto;
import com.backend.ecommerce.application.dto.user.RegisterDto;
import com.backend.ecommerce.domain.entities.User;
import com.backend.ecommerce.domain.enums.UserRole;
import com.backend.ecommerce.infastructure.repositories.UserRepositoryImpl;
import com.backend.ecommerce.shared.exceptions.UniquenessException;
import com.backend.ecommerce.shared.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class AuthServiceImpl {

    private final UserRepositoryImpl userRepo;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepositoryImpl userRepo, JwtUtil jwtUtil, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(RegisterDto registeredUser){
        // Check if the email already exists
        Optional<User> existingUser = userRepo.getUserByEmail(registeredUser.email());
        if (existingUser.isPresent()) {
            throw new UniquenessException("Email already exists");
        }

        var passwordEncoded = passwordEncoder.encode(registeredUser.password());
        User user = new User(registeredUser.name(), registeredUser.email(), passwordEncoded,
                UserRole.USER);

        userRepo.save(user);

        return jwtUtil.generateToken(user);
    }

    public String authenticate(@RequestBody LoginDto user){
        try {
        Authentication auth = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.email(), user.password()));
        }
        catch (AuthenticationException e) {
            return "Invalid Login Credentials";
        }


        User userOptional = userRepo.getUserByEmail(user.email())
                .orElseThrow(() -> new UsernameNotFoundException("No user found"));

        return jwtUtil.generateToken(userOptional);
    }

    public List<User> findAll(){
        return userRepo.getAllUsers();
    }
    public Optional<User> findOne(UUID userId){
        return userRepo.getUserById(userId);
    }

}
