package com.backend.ecommerce.presentation;

import com.backend.ecommerce.abstraction.ProductService;
import com.backend.ecommerce.application.ProductServiceImpl;
import com.backend.ecommerce.application.dto.product.CreateProductDto;
import com.backend.ecommerce.application.dto.product.ProductDto;
import com.backend.ecommerce.application.dto.product.UpdateProductDto;
import com.backend.ecommerce.domain.entities.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<ProductDto>> getProduct(Optional<String> searchValue){
        var products = productService.getAllProducts(searchValue);

        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") UUID id){
        var product = productService.getProductById(id);

        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping()
    public ResponseEntity<ProductDto> createProduct(@RequestBody CreateProductDto product){
        var result = productService.addProduct(product);

        return ResponseEntity.ok(result);
    }

    @PutMapping()
    public ResponseEntity<ProductDto> updateProduct(@RequestBody UpdateProductDto product){
        var result = productService.updateProduct(product);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") UUID id){
        productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }

}
