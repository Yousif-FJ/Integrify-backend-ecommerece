package com.backend.ecommerce.domain.interfaces;

import com.backend.ecommerce.domain.entities.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    public List<Product> getAllProducts();
    public List<Product> searchProducts(String keyword);
    public Optional<Product> getProductById(UUID id);
    public Product addProduct(Product product);
    public List<Product> getProductsByIds(List<UUID> ids);
    public Product updateProduct(Product product);
    public void deleteProduct(UUID id);
}
