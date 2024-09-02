package com.backend.ecommerce.infastructure.repositories;

import com.backend.ecommerce.domain.entities.Product;
import com.backend.ecommerce.domain.interfaces.ProductRepository;
import com.backend.ecommerce.infastructure.jpaRepositories.JpaProductRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

    private final JpaProductRepository jpaProductRepository;

    public ProductRepositoryImpl(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return jpaProductRepository.findAll().stream().toList();
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return jpaProductRepository.findByNameContainingIgnoreCase(keyword);
    }

    @Override
    public Optional<Product> getProductById(UUID id) {
        return jpaProductRepository.findById(id);
    }

    @Override
    public Product addProduct(Product product) {
        jpaProductRepository.save(product);

        return product;
    }

    @Override
    public Product updateProduct(Product product) {
        jpaProductRepository.save(product);

        return product;
    }

    @Override
    public void deleteProduct(UUID id) {
        var originalProduct = jpaProductRepository.findById(id);

        if (originalProduct.isEmpty()) {
            return;
        }
        jpaProductRepository.deleteById(id);
    }

}
