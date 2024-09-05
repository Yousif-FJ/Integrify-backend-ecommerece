package com.backend.ecommerce.application.mapper;

import com.backend.ecommerce.application.dto.dtoInterfaces.IOrderDetailsDto;
import com.backend.ecommerce.application.dto.order.CreateOrderDto;
import com.backend.ecommerce.application.dto.order.OrderDetailsDto;
import com.backend.ecommerce.domain.entities.Order;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.FIELD)
public interface OrderMapper {
  OrderDetailsDto toOrderDetailsDtoFromInterface(IOrderDetailsDto source);
  Order toOrderFromCreateOrderDto(CreateOrderDto source);
}