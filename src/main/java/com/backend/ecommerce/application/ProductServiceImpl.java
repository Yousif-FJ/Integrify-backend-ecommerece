package com.backend.ecommerce.application;

import com.backend.ecommerce.abstraction.ProductService;
import com.backend.ecommerce.application.dto.product.CreateProductDto;
import com.backend.ecommerce.application.dto.product.ProductDto;
import com.backend.ecommerce.application.dto.product.UpdateProductDto;
import com.backend.ecommerce.application.mapper.ProductMapper;
import com.backend.ecommerce.domain.entities.Product;
import com.backend.ecommerce.domain.interfaces.CategoryRepository;
import com.backend.ecommerce.domain.interfaces.ProductRepository;
import com.backend.ecommerce.shared.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }

    public List<ProductDto> getAllProducts(Optional<String> searchValue) {
        List<Product> products;
        if (searchValue.isPresent()) {
            products = productRepository.searchProducts(searchValue.get());
        } else {
            products = productRepository.getAllProducts();
        }

        return productMapper.toProductListDto(products);
    }

    public Optional<ProductDto> getProductById(UUID id) {
        var product = productRepository.getProductById(id);

        return product.map(productMapper::toProductDto);
    }

    public ProductDto addProduct(CreateProductDto product) {
        var newProduct = productMapper.toProduct(product);

        var category = categoryRepository.findById(product.categoryId());

        if (category.isEmpty()){
            throw new BadRequestException("Category not found");
        }

        newProduct = productRepository.addProduct(newProduct);

        return productMapper.toProductDto(newProduct);
    }

    public ProductDto updateProduct(UpdateProductDto product) {
        var updateProductDomain = productMapper.toProductFromUpdate(product);

        var oldProduct = productRepository.getProductById(product.id());

        if (oldProduct.isEmpty()){
            throw new BadRequestException("Product not found");
        }

        productRepository.updateProduct(updateProductDomain);

        return productMapper.toProductDto(updateProductDomain);
    }
    public void deleteProduct(UUID id) {
        productRepository.deleteProduct(id);
    }
}
