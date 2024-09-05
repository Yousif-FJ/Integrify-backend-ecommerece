package com.backend.ecommerce.application;

import com.backend.ecommerce.abstraction.OrderService;
import com.backend.ecommerce.application.dto.dtoInterfaces.IOrderDetailsDto;
import com.backend.ecommerce.application.dto.order.OrderDetailsDto;
import com.backend.ecommerce.application.dto.order.OrderUpdateDto;
import com.backend.ecommerce.application.mapper.OrderMapper;
import com.backend.ecommerce.application.dto.order.CreateOrderDto;
import com.backend.ecommerce.application.dto.dtoInterfaces.IOrderDto;
import com.backend.ecommerce.domain.entities.*;
import com.backend.ecommerce.domain.enums.UserRole;
import com.backend.ecommerce.domain.interfaces.ProductRepository;
import com.backend.ecommerce.domain.interfaces.UserRepository;
import com.backend.ecommerce.infastructure.jpaRepositories.JpaOrderProductRepository;
import com.backend.ecommerce.infastructure.jpaRepositories.JpaOrderRepository;
import com.backend.ecommerce.infastructure.jpaRepositories.JpaPaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    JpaOrderRepository jpaOrderRepository;
    @Autowired
    JpaOrderProductRepository jpaOrderProductRepository;
    @Autowired
    JpaPaymentRepository jpaPaymentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderMapper orderMapper;

    @Override
    public List<IOrderDto> getAllOrders() {
        return jpaOrderRepository.getAllOrders();
    }

    @Override
    public List<IOrderDto> getOrdersByUserId(UUID id) {
        return jpaOrderRepository.getUserOrders(id);
    }


    @Override
    public Optional<OrderDetailsDto> findOrder(UUID id, User user) {

        var orderOpt = jpaOrderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            return Optional.empty();
        }
        if (orderOpt.get().getUser().getId() != user.getId() && user.getUserRole() != UserRole.admin) {
            return Optional.empty();
        }

        var order = orderOpt.get();

        return Optional.of(new OrderDetailsDto(order.getId(), order.getUser().getId(), order.getPayment().getId(),
                order.getUser().getUsername(), order.getUser().getEmail(), order.getStatus(), order.getCity(),
                order.getStreet(), order.getPostNumber(), order.getDate(), order.getPayment().getAmount(),
                order.getPayment().getCity(), order.getPayment().getStreet(), order.getPayment().getPostNumber(),
                order.getPayment().isPaymentStatus()));
    }

    @Override
    public Optional<OrderDetailsDto> updateOrder(UUID id, OrderUpdateDto orderUpdate) {
        Optional<Order> foundOrder = jpaOrderRepository.findById(id);

        if (foundOrder.isEmpty()) return Optional.empty();

        Order order = foundOrder.get();

        order.setId(id);
        order.setCity(orderUpdate.city());
        order.setStreet(orderUpdate.street());
        order.setPostNumber(orderUpdate.postNumber());
        order.setStatus(orderUpdate.status());

        jpaOrderRepository.updateOrder(order);
        Optional<IOrderDetailsDto> newOrder = jpaOrderRepository.getOrderDetails(id);
        return newOrder.map(iOrderDetailsDto -> orderMapper.toOrderDetailsDtoFromInterface(iOrderDetailsDto));
    }

    @Override
    public boolean deleteOrder(UUID id) {
        if (jpaOrderRepository.findById(id).isPresent()) {
            jpaOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public Optional<OrderDetailsDto> createNewOrder(CreateOrderDto createOrderDto, User user) {
        Order order = orderMapper.toOrderFromCreateOrderDto(createOrderDto);
        order.setUser(user);
        order.setDate(LocalDate.now());

        Order newOrder = jpaOrderRepository.save(order);

        var orderedProducts = productRepository.getProductsByIds(createOrderDto.productsId());

        List<OrderProduct> opList = new ArrayList<>();

        float orderSum = 0;
        for (Product product : orderedProducts) {
            opList.add(new OrderProduct(order, product, product.getPrice()));
            orderSum += (float) product.getPrice();
        }

        jpaOrderProductRepository.saveAll(opList);

        var payment = new Payment(orderSum, createOrderDto.city(),
                createOrderDto.street(), createOrderDto.postNumber());
        payment.setOrder(order);
        order.setPayment(payment);

        jpaPaymentRepository.save(payment);

        return Optional.of(new OrderDetailsDto(order.getId(), order.getUser().getId(), order.getPayment().getId(),
                order.getUser().getUsername(), order.getUser().getEmail(), order.getStatus(), order.getCity(),
                order.getStreet(), order.getPostNumber(), order.getDate(), order.getPayment().getAmount(),
                payment.getCity(), payment.getStreet(), payment.getPostNumber(),
                order.getPayment().isPaymentStatus()));
    }
}
