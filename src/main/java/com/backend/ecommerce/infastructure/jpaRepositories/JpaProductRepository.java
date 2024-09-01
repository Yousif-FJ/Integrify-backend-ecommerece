package com.backend.ecommerce.infastructure.jpaRepositories;


import com.backend.ecommerce.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<Product, UUID> {
    public List<Product> findByNameContaining(String value);
}
