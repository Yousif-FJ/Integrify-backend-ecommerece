package com.backend.ecommerce.application;

import com.backend.ecommerce.application.dto.user.UserDto;
import com.backend.ecommerce.domain.entities.User;
import com.backend.ecommerce.domain.interfaces.UserRepository;
import com.backend.ecommerce.shared.exceptions.BadRequestException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(u -> new UserDto(u.getId(), u.getName(), u.getEmail(), u.getUserRole()))
                .toList();
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.getUserById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var result = userRepository.getUserByEmail(username);

        return result.orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDto updateUser(UserDto userDto) {

        var oldUser = userRepository.getUserById(userDto.id());

        if (oldUser.isEmpty()){
            throw new BadRequestException("User not found");
        }

        var updatedUser = new User(userDto.id(), userDto.name(), userDto.email(), oldUser.get().getPassword(),
                userDto.role());

        userRepository.save(updatedUser);

        return userDto;
    }
}
