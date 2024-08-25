package com.backend.ecommerce.infastructure.jpaRepositories;

import com.backend.ecommerce.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByEmail(String email);
}
