package com.backend.ecommerce.infastructure.repositories;

import com.backend.ecommerce.domain.entities.User;
import com.backend.ecommerce.domain.interfaces.UserRepository;
import com.backend.ecommerce.infastructure.jpaRepositories.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public List<User> getAllUsers() {
        return jpaUserRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(User::getName))
                .toList();
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return jpaUserRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        jpaUserRepository.save(user);
    }

    @Override
    public void delete(User user) {
        jpaUserRepository.delete(user);
    }
}
