package com.backend.ecommerce.application.mapper;

import com.backend.ecommerce.application.dto.product.CreateProductDto;
import com.backend.ecommerce.application.dto.product.ProductDto;
import com.backend.ecommerce.application.dto.product.UpdateProductDto;
import com.backend.ecommerce.domain.entities.Product;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface ProductMapper {
    List<ProductDto> toProductListDto(List<Product> products);

    Product toProduct(CreateProductDto source);

    @Mapping(target = "category.id", source = "categoryId")
    Product toProductFromUpdate(UpdateProductDto source);

    ProductDto toProductDto(Product source);
}
